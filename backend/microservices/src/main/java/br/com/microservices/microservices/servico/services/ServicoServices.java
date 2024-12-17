package br.com.microservices.microservices.servico.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.microservices.microservices.authentication.interfaces.UsuarioRepository;
import br.com.microservices.microservices.authentication.model.Usuario;
import br.com.microservices.microservices.loja.interfaces.ComercioRepository;
import br.com.microservices.microservices.loja.models.Comercio;
import br.com.microservices.microservices.loja.models.Disponibilidade;
import br.com.microservices.microservices.servico.interfaces.ServicoAgendadoRepository;
import br.com.microservices.microservices.servico.interfaces.ServicoRepository;
import br.com.microservices.microservices.servico.models.Servico;
import br.com.microservices.microservices.servico.models.ServicoAgendado;
import br.com.microservices.microservices.servico.models.DTO.ServicoClienteDTO;
import br.com.microservices.microservices.servico.models.DTO.ServicoDTO;
import jakarta.transaction.Transactional;

@Service
public class ServicoServices {

    @Autowired
    ServicoAgendadoRepository servicoAgendadoRepository;
    @Autowired
    ServicoRepository servicoRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    ComercioRepository comercioRepository;

    public boolean servicoNaAgendaOcupado(ServicoAgendado servico) {
        LocalTime inicio = servico.getHoraDoInicio();
        LocalTime fim = servico.getHoraDoFinal();
        LocalDate dia = servico.getDiaDoSerivoco();
        long tempoDeServico = servico.getServico().getTempoServico();
        Disponibilidade disponibilidade = servico.getDisponibilidade();
        if (inicio.isAfter(fim) || inicio.equals(fim)) {
            throw new IllegalArgumentException("O horário de início deve ser anterior ao horário de término.");
        }
        if (!disponibilidade.isDentroDoHorarioDisponivel(inicio, fim)) {
            throw new IllegalArgumentException("Os horários estão fora da disponibilidade definida.");
        }

        boolean conflito = disponibilidade.getHorariosAgendados().stream()
                .filter(horarioAgendado -> horarioAgendado.getHorarioAgendadoInicio().toLocalDate().equals(dia))
                .anyMatch(horarioAgendado -> {
                    LocalTime inicioExistente = horarioAgendado.getHorarioAgendadoInicio().toLocalTime();
                    LocalTime fimExistente = horarioAgendado.getHorarioAgendadoFim().toLocalTime()
                            .plusMinutes(tempoDeServico);
                    return (inicio.isAfter(fimExistente.minusMinutes(5))
                            && fim.isBefore(inicioExistente.plusMinutes(5)));

                });

        if (conflito) {
            throw new IllegalArgumentException("Horário já ocupado por outro serviço.");
        }
        disponibilidade.setHorarioAgendado(servico.getDiaDoSerivoco().atTime(servico.getHoraDoInicio()),
                servico.getDiaDoSerivoco().atTime(servico.getHoraDoFinal()));
        return conflito;

    }

    @Transactional
    public Servico registrarServicoComUsuario(ServicoClienteDTO servicosDTO) {
        Optional<Servico> servicoOpt = servicoRepository.findById(servicosDTO.getServicoId());
        Usuario usuario = usuarioRepository.encontrarByEmail(servicosDTO.getUsuarioDTO().getEmail());
        Optional<Comercio> comercioOpt = comercioRepository.findByNomeLoja(servicosDTO.getNomeDaLoja());
        if (servicoOpt.isPresent() && usuario != null && comercioOpt.isPresent()) {
            Disponibilidade disponibilidade = comercioOpt.get().getDisponibilidades();
            Servico servico = servicoOpt.get();
            ServicoAgendado agendado = new ServicoAgendado(servico);
            if (disponibilidade.verificarDiaDisponivel(servicosDTO.getAgendamentoDTO().toLocalDate())) {

                agendado.setUsuario(usuario);
                agendado.setServico(servico);
                agendado.setDisponibilidade(disponibilidade);
                agendado.setDiaDoSerivoco(servicosDTO.getAgendamentoDTO().toLocalDate());
                agendado.setHoraDoInicio(servicosDTO.getAgendamentoDTO().toLocalTime());
                agendado.setHoraDoFinal(
                        servicosDTO.getAgendamentoDTO().toLocalTime().plusMinutes(servico.getTempoServico()));
                if (!servicoNaAgendaOcupado(agendado)) {
                    servicoAgendadoRepository.save(agendado);
                    usuario.setServico(agendado);
                    return servico;
                }
            }
        }
        throw new IllegalArgumentException("Solicitação indisponivel verifique a data.");
    }

    @Transactional
    public Servico salvarServico(ServicoDTO servico, String usuarioDTO) {
        var usuario = usuarioRepository.encontrarByUsername(usuarioDTO);
        var comercio = comercioRepository.findById(usuario.getComercio().getNomeLoja()).get();
        Servico servico2 = new Servico();
        servico2.setNomeServico(servico.nomeServico());
        servico2.setValorServico(servico.valorServico());
        servico2.setTempoServico(servico.tempoServico());
        servico2.setComercios(comercio);
        servicoRepository.save(servico2);
        comercio.getServicosDaLoja().add(servico2);
        comercioRepository.save(comercio);
        return servico2;
    }

    public Page<Servico> listarServico(Pageable pageable) {
        return servicoRepository.findAll(pageable);
    }

    public Page<Servico> listarServicoAsc(Pageable pageable) {
        return servicoRepository.findAllByOrderByValorServicoAsc(pageable);
    }

    public List<Servico> produtoPorNome(String nomeProduto) {
        return servicoRepository.findByNomeServico(nomeProduto);
    }

    public void deletarProdutoPorLoja(String nomeComercio, ServicoDTO comercio) {
        var servico = servicoRepository.findByNomeServico(comercio.nomeServico());
        Servico servicoUnico = servico.stream()
                .filter(servicos -> servicos.getComercios() != null &&
                        nomeComercio.equals(nomeComercio))
                .findFirst()
                .orElse(null);
        servicoRepository.delete(servicoUnico);
    }

    public Page<Servico> listarProdutosPorLoja(String nomeComercio, Pageable pageable) {
        Optional<Comercio> comercioOpt = comercioRepository.findByNomeLoja(nomeComercio);
        if (!comercioOpt.isPresent()) {
            throw new RuntimeException("Comércio não encontrado com o nome: " + nomeComercio);
        }

        Comercio comercio = comercioOpt.get();
        return servicoRepository.findByComercios(comercio, pageable);
    }

}
