package br.com.microservices.microservices.authentication.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.microservices.microservices.authentication.model.Usuario;
import br.com.microservices.microservices.authentication.model.UsuarioDTO;
import br.com.microservices.microservices.authentication.services.CustomUserDetailsService;
import br.com.microservices.microservices.authentication.services.TokenService;
import br.com.microservices.microservices.sendemail.services.EmailService;
import br.com.microservices.microservices.servico.exceptions.ErrorDTO;
import br.com.microservices.microservices.servico.exceptions.SuccessResponseException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    CustomUserDetailsService userDetailsService;
    @Autowired
    TokenService tokenService;
    @Autowired
    EmailService emailService;

    @PostMapping("/singin")
    public ResponseEntity<?> login(@RequestBody UsuarioDTO data) {
        var token = userDetailsService.loginDeUsuario(data);
        return ResponseEntity.ok().body(token);
    }

    @PostMapping("/singup/usuario")
    public ResponseEntity<?> register(@Valid @RequestBody UsuarioDTO data, BindingResult bindingResult)
            throws JsonProcessingException {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());

            ErrorDTO errorResponse = new ErrorDTO(
                    LocalDateTime.now(),
                    HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST.getReasonPhrase(),
                    String.join(", ", errorMessages),
                    "/singup/usuario");

            return ResponseEntity.badRequest().body(errorResponse);
        } else {
            Usuario usuario = userDetailsService.novoUsuario(data);
            String token = tokenService.generatedToken(usuario);
            emailService.sendEmailToClientNewCliente(token, usuario.getEmail());
            throw new SuccessResponseException(
                    HttpStatus.CREATED.value(),
                    "Usuário criado e e-mail enviado com sucesso.",
                    data);

        }

    }

    @GetMapping("/singup/{token}")
    public ResponseEntity validarUsuario(@PathVariable("token") String token) {
        userDetailsService.activateUserByToken(token);
        return ResponseEntity.status(HttpStatus.OK).body("E-mail confirmado com sucesso.");
    }

}
