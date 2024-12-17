package br.com.microservices.microservices.sendemail.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.microservices.microservices.sendemail.interfaces.ContatoRepository;
import br.com.microservices.microservices.sendemail.interfaces.LikeRepository;
import br.com.microservices.microservices.sendemail.models.ContatoModel;
import br.com.microservices.microservices.sendemail.models.LikeModel;
import br.com.microservices.microservices.sendemail.services.EmailService;
import br.com.microservices.microservices.servico.exceptions.ErrorDTO;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class EmailRoutes {
    @Autowired
    ContatoRepository contatoRepository;
    @Autowired
    LikeRepository likeRepository;

    @Autowired
    EmailService emailService;

    @PostMapping("/like")
    public void postLiked() {
        likeRepository.save(new LikeModel());
    }

    @GetMapping("/like")
    public long getAllLiked() {
        var count = likeRepository.count();
        return count;
    }

    @PostMapping("/review")
    public ResponseEntity<?> postComentarioPortifolio(@Valid @RequestBody ContatoModel contato,
            BindingResult bindingResult) throws JsonProcessingException {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());

            ErrorDTO errorResponse = new ErrorDTO(
                    LocalDateTime.now(),
                    HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST.getReasonPhrase(),
                    String.join(", ", errorMessages),
                    "/api/review");

            return ResponseEntity.badRequest().body(errorResponse);
        } else {
            emailService.sendEmailToReview(contato);
            return ResponseEntity.status(HttpStatus.OK).body("Email enviado com sucesso");
        }
    }

    @GetMapping("/contato")
    public ResponseEntity<List<ContatoModel>> getComment() {
        var contato = contatoRepository.findAll();
        return ResponseEntity.ok().body(contato);
    }

}
