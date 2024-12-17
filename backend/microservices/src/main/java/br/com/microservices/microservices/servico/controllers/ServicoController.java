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

import br.com.microservices.microservices.authentication.services.TokenService;
import br.com.microservices.microservices.loja.services.ComercioServices;
import br.com.microservices.microservices.servico.models.Servico;
import br.com.microservices.microservices.servico.models.DTO.ServicoClienteDTO;
import br.com.microservices.microservices.servico.models.DTO.ServicoDTO;
import br.com.microservices.microservices.servico.services.ServicoServices;

@RequestMapping("/comercio")
@RestController
public class ServicoController {

    @Autowired
    ServicoServices services;
    @Autowired
    ComercioServices comercioServices;
    @Autowired
    TokenService tokenService;

    @GetMapping("/{nomeDaLoja}/servicos")
    public ResponseEntity<Page<Servico>> listarServicos(
            @PathVariable String nomeDaLoja,
            @PageableDefault(sort = "valorServico", direction = Sort.Direction.ASC) Pageable pageable) {
        if (nomeDaLoja.isEmpty()) {
            Page<Servico> itens = services.listarServicoAsc(pageable);
            return ResponseEntity.status(HttpStatus.OK).body(itens);
        }
        if (nomeDaLoja.length() > 0) {
            var produtosListados = services.listarProdutosPorLoja(nomeDaLoja, pageable);
            return ResponseEntity.status(HttpStatus.OK).body(produtosListados);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{nomeDaLoja}/servicos/agendamento")
    public ResponseEntity<Servico> registrarNovoSevico(
            @PathVariable String nomeDaLoja,
            @RequestBody ServicoClienteDTO servico,
            @RequestHeader("authorization") String token) {
        if (nomeDaLoja.length() > 0 && nomeDaLoja.equals(servico.getNomeDaLoja())) {
            var usuario = tokenService.extrairUsuario(token);
            var servicoAdicionado = services.registrarServicoComUsuario(servico);
            return ResponseEntity.status(HttpStatus.OK).body(servicoAdicionado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{nomeDaLoja}/criarServico")
    public ResponseEntity<Servico> criarProduto(
            @RequestBody ServicoDTO servico,
            @RequestHeader("authorization") String token) {
        var usuario = tokenService.extrairUsuario(token);
        var produto = services.salvarServico(servico, usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(produto);

    }

    @DeleteMapping("/{nomeDaLoja}/deletarServico")
    public ResponseEntity deletarProduto(@PathVariable String nomeComercio,
            @RequestBody ServicoDTO comercio) {
        try {
            services.deletarProdutoPorLoja(nomeComercio, comercio);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e);
        }

    }
}