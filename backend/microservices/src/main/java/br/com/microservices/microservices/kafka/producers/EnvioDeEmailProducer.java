// package br.com.microservices.microservices.kafka.producers;

// import java.util.Map;

// import org.apache.kafka.clients.admin.NewTopic;
// import org.apache.kafka.clients.producer.ProducerConfig;
// import org.apache.kafka.common.serialization.StringSerializer;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.kafka.config.TopicBuilder;
// import org.springframework.kafka.core.DefaultKafkaProducerFactory;
// import org.springframework.kafka.core.KafkaTemplate;
// import org.springframework.kafka.core.ProducerFactory;

// @Configuration
// public class EnvioDeEmailProducer {
//     @Autowired
//     KafkaProperties kafkaProperties;
//     @Value("${app.kafka.topic.email}")
//     String emailRequestTopic;

//     @Bean(name = "emailKafkaTemplate")
//     public ProducerFactory<String, String> emailProducerFactory() {
//         Map<String, Object> properties = kafkaProperties.buildProducerProperties(null);
//         properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//         properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//         return new DefaultKafkaProducerFactory<>(properties);
//     }

//     @Bean
//     public KafkaTemplate<String, String> emailKafkaTemplate() {
//         return new KafkaTemplate<>(emailProducerFactory());
//     }

//     @Bean
//     public NewTopic criarEnvioDeEmailRequestTopic() {
//         return TopicBuilder.name(emailRequestTopic)
//                 .partitions(1)
//                 .replicas(1)
//                 .build();
//     }

// }
