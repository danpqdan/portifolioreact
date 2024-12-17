package br.com.microservices.microservices.loja.services;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.microservices.microservices.authentication.interfaces.UsuarioRepository;
import br.com.microservices.microservices.authentication.model.Roles;
import br.com.microservices.microservices.authentication.model.Usuario;
import br.com.microservices.microservices.loja.interfaces.ComercioRepository;
import br.com.microservices.microservices.loja.interfaces.DisponibilidadeRepository;
import br.com.microservices.microservices.loja.models.Calendario;
import br.com.microservices.microservices.loja.models.Comercio;
import br.com.microservices.microservices.loja.models.Disponibilidade;
import br.com.microservices.microservices.loja.models.DTO.BuscaDisponibilidadeDTO;
import br.com.microservices.microservices.loja.models.DTO.ComercioDTO;
import br.com.microservices.microservices.loja.models.DTO.FuncionamentoDTO;
import br.com.microservices.microservices.servico.interfaces.ServicoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ComercioServices {

    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    ComercioRepository comercioRepository;
    @Autowired
    DisponibilidadeRepository disponibilidadeRepository;
    @Autowired
    ServicoRepository services;

    @Transactional
    public Comercio validarAdmin(ComercioDTO dto) {
        try {
            Usuario usuario = usuarioRepository.encontrarByUsername(dto.username());
            Optional<Comercio> comercioOpt = comercioRepository.findByNomeLoja(dto.nomeLoja());
            if (comercioOpt.isPresent()) {
                throw new IllegalAccessError("Nome ja existe");
            }
            Comercio comercio = new Comercio();
            comercio.setNomeLoja(dto.nomeLoja());
            comercio.setEnderecoLoja(dto.endereco());
            comercio.setDonoDaLoja(usuario);
            var saved = comercioRepository.saveAndFlush(comercio);
            usuario.setComercio(comercio);
            usuarioRepository.saveAndFlush(usuario);
            if (saved.getNomeLoja() != null) {
                usuario.setRole(Set.of(Roles.DONO));
            }
            return comercio;
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    @Transactional
    public Comercio criarHorarioDeFuncionamento(FuncionamentoDTO funcionamentoDTO) {
        Optional<Comercio> comercioOpt = comercioRepository.findByNomeLoja(funcionamentoDTO.getNomeLoja());
        if (comercioOpt.isEmpty()) {
            throw new UsernameNotFoundException("Nao foi encontrado: " + funcionamentoDTO.getNomeLoja());
        }
        var comercio = comercioOpt.get();
        Calendario calendario = new Calendario();
        calendario.setDiasDeFolga(funcionamentoDTO.getDiasFechado());
        var newCalendar = calendario.atualizarDiasIndisponiveis(funcionamentoDTO.getDiasFechado(),
                funcionamentoDTO.getDiaDeFolga());
        Disponibilidade disponibilidade = new Disponibilidade();
        if (comercio.getDisponibilidades() == null) {
            disponibilidade.setHorarioAbertura(funcionamentoDTO.getHorarioDeAbertura().toLocalTime());
            disponibilidade.setHorarioFechamento(funcionamentoDTO.getHorarioDeFechamento().toLocalTime());
            disponibilidade.setCalendarioDeFuncionamento(newCalendar);
            disponibilidade.setComercio(comercio);
            comercio.setDisponibilidades(disponibilidade);
            comercioRepository.save(comercio);
            disponibilidadeRepository.save(disponibilidade);
            return comercio;
        } else {

            disponibilidade.setHorarioAbertura(funcionamentoDTO.getHorarioDeAbertura().toLocalTime());
            disponibilidade.setHorarioFechamento(funcionamentoDTO.getHorarioDeFechamento().toLocalTime());
            disponibilidade.setCalendarioDeFuncionamento(newCalendar);
            disponibilidadeRepository.saveAndFlush(disponibilidade);
            comercio.setDisponibilidades(disponibilidade);
            comercioRepository.save(comercio);
            return comercio;
        }
    }

    @Transactional
    public Comercio criarFolgaExcpetion(FuncionamentoDTO funcionamentoDTO) {
        Comercio comercio = comercioRepository.findByNomeLoja(funcionamentoDTO.getNomeLoja())
                .orElseThrow(() -> new EntityNotFoundException("Comércio não encontrado"));
        Disponibilidade disponibilidade = disponibilidadeRepository.findByComercio_NomeLoja(comercio.getNomeLoja());
        disponibilidade.setCalendarioDeFuncionamento(new Calendario()
                .atualizarDiasIndisponiveis(funcionamentoDTO.getDiasFechado(), funcionamentoDTO.getDiaDeFolga()));
        disponibilidadeRepository.saveAndFlush(disponibilidade);

        return comercio;
    }

    public Comercio retornarDisponibilidades(String nomeDoComercio, BuscaDisponibilidadeDTO buscaDisponibilidadeDTO) {
        Disponibilidade disponibilidade = disponibilidadeRepository.findByComercio_NomeLoja(nomeDoComercio);
        if (disponibilidade == null) {
            throw new IllegalArgumentException("Disponibilidade não encontrada para o comércio: " + nomeDoComercio);
        }
        LocalDate dataBusca = buscaDisponibilidadeDTO.toLocalDate();
        boolean disponivel = disponibilidade.verificarDiaDisponivel(dataBusca);
        if (!disponivel) {
            throw new IllegalArgumentException("A data " + dataBusca + " não está disponível para o comércio.");
        }
        return disponibilidade.getComercio();
    }

}
