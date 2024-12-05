package br.com.microservices.microservices.servico.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.microservices.microservices.authentication.interfaces.UsuarioRepository;
import br.com.microservices.microservices.loja.interfaces.ComercioRepository;
import br.com.microservices.microservices.loja.models.ServicosPorComercio;
import br.com.microservices.microservices.servico.interfaces.ServicoRepository;
import br.com.microservices.microservices.servico.models.Servico;
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
    public Servico salvarServico(ServicosPorComercio servico) {
        var usuario = usuarioRepository.encontrarByEmail(servico.email());
        var comercio = comercioRepository.findById(usuario.getComercio().getId()).get();
        Servico servico2 = new Servico();
        servico2.setNomeServico(servico.nomeServico());
        servico2.setValorServico(servico.valorServico());
        servico2.setTempoServico(servico.tempoServico());
        servico2.setComercios(comercio);
        servicoRepository.save(servico2);
        comercio.getServicos().add(servico2);
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

    public void deletarProdutoPorLoja(String nomeComercio, ServicosPorComercio comercio) {
        var servico = servicoRepository.findByNomeServico(comercio.nomeServico());
        Servico servicoUnico = servico.stream()
                .filter(servicos -> servicos.getComercios() != null &&
                        nomeComercio.equals(nomeComercio))
                .findFirst()
                .orElse(null);
        servicoRepository.delete(servicoUnico);
    }

}
