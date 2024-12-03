package br.com.microservices.microservices.produto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.microservices.microservices.produto.models.Servico;
import br.com.microservices.microservices.produto.services.ServicoManagerFactory;

@RequestMapping("/api/servicos")
@RestController
public class RouteController {

    @Autowired
    ServicoManagerFactory servicoManagerFactory;

    @GetMapping
    public ResponseEntity<Page<Servico>> listarProdutos(Pageable pageable) {
        Page<Servico> itens = servicoManagerFactory.findAll(pageable);

        if (itens.hasContent()) {
            return ResponseEntity.status(HttpStatus.OK).body(itens);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Servico> criarProduto(@RequestBody Servico servico) {
        var produto = servicoManagerFactory.save(servico);
        return ResponseEntity.status(HttpStatus.CREATED).body(produto);

    }

}
