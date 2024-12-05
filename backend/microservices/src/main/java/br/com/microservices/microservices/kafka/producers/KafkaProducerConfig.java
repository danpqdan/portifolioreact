package br.com.microservices.microservices.kafka.producers;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.producer.key-serializer}")
    private String keySerializer;

    @Value("${spring.kafka.producer.value-serializer}")
    private String valueSerializer;

    // Método para criar a fábrica de produtores com tipo específico
    private <T> DefaultKafkaProducerFactory<String, T> createProducerFactory(Class<T> valueType) {
        Map<String, Object> producerProps = new HashMap<>();
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        // Usando o JsonSerializer corretamente para tipos específicos
        return new DefaultKafkaProducerFactory<>(producerProps);
    }

    // KafkaTemplate para enviar AuthenticationDTO
    @Bean(name = "usuariokafkaTemplate")
    public KafkaTemplate<String, Object> usuarioKafkaTemplate() {
        return new KafkaTemplate<>(createProducerFactory(Object.class));
    }

    // KafkaTemplate para enviar emails
    @Bean(name = "emailkafkaTemplate")
    public KafkaTemplate<String, Object> emailKafkaTemplate() {
        return new KafkaTemplate<>(createProducerFactory(Object.class));
    }
}
