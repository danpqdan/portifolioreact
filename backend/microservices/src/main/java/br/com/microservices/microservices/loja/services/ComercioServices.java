package br.com.microservices.microservices.loja.services;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.microservices.microservices.authentication.interfaces.UsuarioRepository;
import br.com.microservices.microservices.authentication.model.Roles;
import br.com.microservices.microservices.authentication.model.Usuario;
import br.com.microservices.microservices.loja.interfaces.CalendarioRepository;
import br.com.microservices.microservices.loja.interfaces.ComercioRepository;
import br.com.microservices.microservices.loja.models.Comercio;
import br.com.microservices.microservices.loja.models.DiaEHorarioDeFuncionamento;
import br.com.microservices.microservices.loja.models.DTO.ComercioDTO;
import br.com.microservices.microservices.loja.models.DTO.FuncionamentoDTO;
import br.com.microservices.microservices.servico.interfaces.ServicoRepository;
import br.com.microservices.microservices.servico.models.Servico;
import jakarta.transaction.Transactional;

@Service
public class ComercioServices {

    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    ComercioRepository comercioRepository;
    @Autowired
    CalendarioRepository calendarioRepository;
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
            if (saved.getId() != null) {
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
        if (!comercioOpt.isPresent()) {
            throw new UsernameNotFoundException("Nao foi encontrado: " + funcionamentoDTO.getNomeLoja());
        }
        var comercio = comercioOpt.get();
        DiaEHorarioDeFuncionamento disponibilidade = new DiaEHorarioDeFuncionamento();
        Year anoAtual = Year.of(LocalDate.now().getYear()); // Obtendo o ano atual como Year
        disponibilidade.diasDeFuncionamento(funcionamentoDTO.getDiasDeFolga(), anoAtual);
        disponibilidade.setHorarioAbertura(funcionamentoDTO.getHorarioDeAbertura().toLocalTime());
        disponibilidade.setHorarioFechamento(funcionamentoDTO.getHorarioDeFechamento().toLocalTime());
        disponibilidade.setComercio(comercio);
        disponibilidade.setHorarioTotalDeFuncionamento(funcionamentoDTO.getHorarioDeAbertura().toLocalTime(),
                funcionamentoDTO.getHorarioDeFechamento().toLocalTime());
        calendarioRepository.save(disponibilidade);
        comercio.getDisponibilidades().add(disponibilidade);
        comercioRepository.save(comercio);
        return comercio;
    }

    public Page<Servico> listarProdutosPorLoja(String nomeComercio, Pageable pageable) {
        Optional<Comercio> comercioOpt = comercioRepository.findByNomeLoja(nomeComercio);
        if (!comercioOpt.isPresent()) {
            throw new RuntimeException("Comércio não encontrado com o nome: " + nomeComercio);
        }

        Comercio comercio = comercioOpt.get();
        return services.findByComercios(comercio, pageable);

    }

}
