package br.com.microservices.microservices.loja.services;

import java.util.List;
import java.util.Optional;

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
import br.com.microservices.microservices.loja.models.ComercioDTO;
import br.com.microservices.microservices.loja.models.Disponibilidade;
import br.com.microservices.microservices.loja.models.FuncionamentoDTO;
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
                usuario.setRole(Roles.ADMIN);
            }
            return comercio;
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    @Transactional
    public Comercio criarHorarioDeFuncionamento(FuncionamentoDTO funcionamentoDTO) {
        Optional<Comercio> comercioOpt = comercioRepository.findByNomeLoja(funcionamentoDTO.nomeLoja());
        if (!comercioOpt.isPresent()) {
            throw new UsernameNotFoundException("Nao foi encontrado: " + funcionamentoDTO.nomeLoja());
        }
        var comercio = comercioOpt.get();
        Disponibilidade disponibilidade = new Disponibilidade();
        disponibilidade.setData(funcionamentoDTO.data());
        disponibilidade.setHorarioAbertura(funcionamentoDTO.horarioDeAbertura());
        disponibilidade.setHorarioFechamento(funcionamentoDTO.horarioDeFechamento());
        disponibilidade.setComercio(comercio);
        calendarioRepository.save(disponibilidade);
        comercio.setDisponibilidades(List.of(disponibilidade));
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
