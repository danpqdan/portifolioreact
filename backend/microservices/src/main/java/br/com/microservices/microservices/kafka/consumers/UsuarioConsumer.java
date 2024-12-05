package br.com.microservices.microservices.kafka.consumers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.microservices.microservices.authentication.interfaces.UsuarioRepository;
import br.com.microservices.microservices.authentication.model.Usuario;
import br.com.microservices.microservices.authentication.services.CustomUserDetailsService;
import br.com.microservices.microservices.authentication.services.TokenService;
import br.com.microservices.microservices.sendemail.services.EmailService;

@Service
public class UsuarioConsumer {

    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    EmailService emailService;
    @Autowired
    TokenService tokenService;
    @Autowired
    @Qualifier("emailkafkaTemplate")
    private KafkaTemplate<String, Object> emailkafkaTemplate;
    @Autowired
    CustomUserDetailsService userDetailsService;

    @KafkaListener(topics = "${app.kafka.topic.usuarios}", groupId = "grupo-usuarios")
    public void criarUsuarioParaConsumo(Usuario usuario) {
        try {
            usuarioRepository.save(usuario);
        } catch (Exception e) {
            System.out.println("Erro ao salvar no banco: " + e.getMessage());
            throw new RuntimeException("Erro ao criar o usuário para o tópico Kafka", e);
        }
    }

    @KafkaListener(topics = "${app.kafka.topic.emails}", groupId = "grupo-usuarios")
    public void criarEmailDeConsumoUsuario(Usuario usuario) {
        var token = tokenService.generatedToken(usuario);
        String bodyEmail = String.format(
                "Acesse o link para ativar seu usuário: <a href='http://localhost:8080/auth/singup/%s'>Ativar Conta</a>",
                token);
        emailService.sendEmailToClient(usuario.getEmail(), "Ativação de usuário", usuario.toString() + bodyEmail);
    }

}
