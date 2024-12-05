package br.com.microservices.microservices.loja.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.microservices.microservices.authentication.services.CustomUserDetailsService;
import br.com.microservices.microservices.authentication.services.TokenService;
import br.com.microservices.microservices.loja.models.Comercio;
import br.com.microservices.microservices.loja.models.ComercioDTO;
import br.com.microservices.microservices.loja.models.FuncionamentoDTO;
import br.com.microservices.microservices.loja.services.ComercioServices;

@RequestMapping("/comercio")
@RestController
public class ComercioController {

    @Autowired
    TokenService tokenService;
    @Autowired
    CustomUserDetailsService userDetailsService;
    @Autowired
    ComercioServices comercioServices;

    @PostMapping("/criar")
    public ResponseEntity validarLoja(
            @RequestBody ComercioDTO admin,
            @RequestHeader("authorization") String token) {
        try {
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7); // Remove "Bearer " (7 caracteres)
            }
            var usuarioToken = tokenService.extrairUsuario(token);
            if (usuarioToken.trim().equals(admin.username().trim())) {
                comercioServices
                        .validarAdmin(admin);
                return ResponseEntity.status(HttpStatus.OK).body("Administrador criado!");
            }
            throw new SecurityException("Solicitação invalida");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/{nomeDoComercio}/horarios")
    public ResponseEntity<Comercio> adicionarHorariosDeFuncionamento(
            @PathVariable("nomeDoComercio") String nomeDoComercio, @RequestBody FuncionamentoDTO funcionamentoDTO) {
        try {
            if (nomeDoComercio != funcionamentoDTO.nomeLoja()) {
                throw new IllegalAccessException("Not accept request");
            }
            var funcionamento = comercioServices.criarHorarioDeFuncionamento(funcionamentoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(funcionamento);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
