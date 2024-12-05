package br.com.microservices.microservices.sendemail.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.microservices.microservices.sendemail.interfaces.ContatoRepository;
import br.com.microservices.microservices.sendemail.interfaces.LikeRepository;
import br.com.microservices.microservices.sendemail.models.ContatoDTO;
import br.com.microservices.microservices.sendemail.models.ContatoModel;
import br.com.microservices.microservices.sendemail.models.LikeModel;
import br.com.microservices.microservices.sendemail.services.EmailService;

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

    @PostMapping("/contato")
    public String postComment(@RequestBody ContatoDTO dto) {
        try {
            emailService.sendEmailToClient("danieltisantos@gmail.com", dto.subject(), dto.body());
            contatoRepository.save(new ContatoModel(dto.subject(), dto.body(), dto.review()));
            return "Email enviado com sucesso";
        } catch (Error e) {
            return e.toString();
        }
    }

    @GetMapping("/contato")
    public ResponseEntity<List<ContatoModel>> getComment() {
        var contato = contatoRepository.findAll();
        return ResponseEntity.ok().body(contato);
    }

}
