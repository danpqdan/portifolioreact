package br.com.microservices.microservices.kafka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UsuarioProducerService {

    @Value("${app.kafka.topic.usuarios}")
    String usuarioRequestTopic;
    @Value("${app.kafka.topic.emails}")
    String emailRequestTopic;

    @Autowired
    @Qualifier("usuariokafkaTemplate") // Specify the correct bean by name
    private KafkaTemplate<String, Object> usuarioKafkaTemplate;

    @Autowired
    @Qualifier("emailkafkaTemplate")
    private KafkaTemplate<String, Object> emailKafkaTemplate;
    @Autowired
    ObjectMapper objectMapper;

    public void sendUsuario(Object usuario) throws JsonProcessingException {
        usuarioKafkaTemplate.send(usuarioRequestTopic, usuario);

    }

    public void sendEmail(Object usuario) throws JsonProcessingException {
        emailKafkaTemplate.send(emailRequestTopic, usuario);
    }
}
