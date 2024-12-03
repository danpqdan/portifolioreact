package br.com.microservices.microservices.produto.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.microservices.microservices.produto.models.Servico;

public interface ServicoRepository extends JpaRepository<Servico, Integer> {
    Page<Servico> findAllByOrderByNomeServicoAsc(Pageable pageable);

    void deleteByNomeServico(String nomeServico);
}
