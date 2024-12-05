package br.com.microservices.microservices.servico.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.microservices.microservices.loja.models.ServicosPorComercio;
import br.com.microservices.microservices.loja.services.ComercioServices;
import br.com.microservices.microservices.servico.models.Servico;
import br.com.microservices.microservices.servico.services.ServicoServices;

@RequestMapping("/servicos")
@RestController
public class ServicoController {

    @Autowired
    ServicoServices services;
    @Autowired
    ComercioServices comercioServices;

    @GetMapping("/loja/{nomeComercio}")
    public ResponseEntity<Page<Servico>> listarServicos(
            @PathVariable String nomeComercio,
            @PageableDefault(sort = "valorServico", direction = Sort.Direction.ASC) Pageable pageable) {
        if (nomeComercio.isEmpty()) {
            Page<Servico> itens = services.listarServicoAsc(pageable);
            return ResponseEntity.status(HttpStatus.OK).body(itens);
        }
        if (nomeComercio.length() > 0) {
            var produtosListados = comercioServices.listarProdutosPorLoja(nomeComercio, pageable);
            return ResponseEntity.status(HttpStatus.OK).body(produtosListados);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Servico> criarProduto(@RequestBody ServicosPorComercio servico,
            @RequestHeader("authorization") String token) {

        var produto = services.salvarServico(servico);
        return ResponseEntity.status(HttpStatus.CREATED).body(produto);

    }

    @DeleteMapping("/loja/{nomeComercio}")
    public ResponseEntity deletarProduto(@PathVariable String nomeComercio,
            @RequestBody ServicosPorComercio comercio) {
        try {
            services.deletarProdutoPorLoja(nomeComercio, comercio);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e);
        }

    }
}