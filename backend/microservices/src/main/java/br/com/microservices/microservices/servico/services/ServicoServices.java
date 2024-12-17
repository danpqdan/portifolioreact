package br.com.microservices.microservices.servico.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.microservices.microservices.authentication.interfaces.UsuarioRepository;
import br.com.microservices.microservices.loja.interfaces.ComercioRepository;
import br.com.microservices.microservices.loja.models.Comercio;
import br.com.microservices.microservices.loja.models.Disponibilidade;
import br.com.microservices.microservices.servico.interfaces.ServicoRepository;
import br.com.microservices.microservices.servico.models.Servico;
import br.com.microservices.microservices.servico.models.ServicoAgendado;
import br.com.microservices.microservices.servico.models.DTO.ServicoClienteDTO;
import br.com.microservices.microservices.servico.models.DTO.ServicoDTO;
import jakarta.transaction.Transactional;

@Service
public class ServicoServices {

    @Autowired
    ServicoRepository servicoRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    ComercioRepository comercioRepository;

    @Transactional
    public Servico registrarServicoComUsuario(ServicoClienteDTO servicosDTO) {
        var servicoOpt = servicoRepository.findById(servicosDTO.getServicoId());
        var usuarioOpt = usuarioRepository.encontrarByEmail(servicosDTO.getUsuarioDTO().getEmail());
        var comercioOpt = comercioRepository.findByNomeLoja(servicosDTO.getNomeDaLoja());
        if (servicoOpt.isPresent() && usuarioOpt != null && comercioOpt.isPresent()) {
            Disponibilidade disponibilidade = comercioOpt.get().getDisponibilidades();
            Servico servico = servicoOpt.get();
            ServicoAgendado agendado = new ServicoAgendado();
            if (disponibilidade.verificarDiaDisponivel(servicosDTO.getAgendamentoDTO().toLocalDate())) {

                agendado.setUsuario(usuarioOpt);
                agendado.setComercios(comercioOpt.get());
                agendado.setServico(servico);
                agendado.setDisponibilidade(disponibilidade);
                agendado.setDiaDoSerivoco(servicosDTO.getAgendamentoDTO().toLocalDate());
                agendado.setHoraDoInicio(servicosDTO.getAgendamentoDTO().toLocalTime());
                agendado.setHoraDoFinal(
                        servicosDTO.getAgendamentoDTO().toLocalTime().plusMinutes(servico.getTempoServico()));
                disponibilidade.setServicoNaAgenda(agendado);
                return servico;
            }
            throw new IllegalArgumentException("Solicitação indisponivel verifique a data.");
        }
        return null;
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
