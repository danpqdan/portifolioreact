package br.com.microservices.microservices.loja.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.microservices.microservices.authentication.model.Usuario;
import br.com.microservices.microservices.authentication.model.UsuarioDTO;
import br.com.microservices.microservices.authentication.services.CustomUserDetailsService;
import br.com.microservices.microservices.authentication.services.TokenService;
import br.com.microservices.microservices.loja.models.DTO.ComercioDTO;
import br.com.microservices.microservices.loja.models.DTO.FuncionamentoDTO;
import br.com.microservices.microservices.loja.services.ComercioServices;
import br.com.microservices.microservices.servico.exceptions.ErrorDTO;
import br.com.microservices.microservices.servico.exceptions.SuccessResponseException;
import jakarta.validation.Valid;

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
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Remove "Bearer " (7 caracteres)
        }
        UsuarioDTO usuarioDTO = new UsuarioDTO(admin.email(), admin.username(), admin.password());
        userDetailsService.validarUsuario(usuarioDTO, token);
        comercioServices
                .validarAdmin(admin);
        return ResponseEntity.status(HttpStatus.OK).body("Administrador criado!");
    }

    @PostMapping("/{nomeDoComercio}/horarios")
    public ResponseEntity<?> adicionarHorariosDeFuncionamento(
            @PathVariable("nomeDoComercio") String nomeComercio,
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody FuncionamentoDTO funcionamentoDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());

            ErrorDTO errorResponse = new ErrorDTO(
                    LocalDateTime.now(),
                    HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST.getReasonPhrase(),
                    String.join(", ", errorMessages),
                    "/comercio/" + nomeComercio + "/usuario");

            return ResponseEntity.badRequest().body(errorResponse);
        } else {
            Usuario usuario = userDetailsService.validarUsuario(funcionamentoDTO.getUsuario(), token);
            if (usuario.getComercio().getNomeLoja().equals(nomeComercio)) {
                comercioServices.criarHorarioDeFuncionamento(funcionamentoDTO);
            }
            throw new SuccessResponseException(
                    HttpStatus.CREATED.value(),
                    "Loja criada, horarios verificados",
                    funcionamentoDTO);
        }
    }
}