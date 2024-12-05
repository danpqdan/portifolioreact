package br.com.microservices.microservices.servico.interfaces;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.microservices.microservices.loja.models.Comercio;
import br.com.microservices.microservices.servico.models.Servico;

public interface ServicoRepository extends JpaRepository<Servico, UUID> {
    Page<Servico> findAllByOrderByValorServicoAsc(Pageable pageable);

    Page<Servico> findAllByOrderByValorServicoDesc(Pageable pageable);

    Page<Servico> findByComercios(Comercio comercio, Pageable pageable);

    List<Servico> findByNomeServico(String nomeServicos);

    void deleteByNomeServico(String nomeServicos);
}
