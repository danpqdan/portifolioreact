/**
 * This Java class defines a REST controller for interacting with Apache Kafka, including methods for
 * retrieving messages from Kafka.
 */
// package br.com.microservices.microservices.kafka.controller;

// import java.util.List;

// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;

// import br.com.microservices.microservices.kafka.KafkaConsumer;
// import br.com.microservices.microservices.kafka.KafkaProducer;

// @RestController
// @RequestMapping("/api/kafka")
// public class KafkaController {

//     private final KafkaProducer kafkaProducer;
//     private final KafkaConsumer kafkaConsumer;

//     public KafkaController(KafkaProducer kafkaProducer, KafkaConsumer kafkaConsumer) {
//         this.kafkaProducer = kafkaProducer;
//         this.kafkaConsumer = kafkaConsumer;
//     }

//     @GetMapping("/messages")
//     public List<String> getMessagesFromKafka() {
//         return kafkaConsumer.getMessages();
//     }

//     // @PostMapping("/send")
//     // public String sendMessageToKafka(@RequestParam String topic, @RequestParam String message) {
//     //     kafkaProducer.sendMessage(topic, message);
//     //     return "Mensagem enviada para o tópico " + topic + ": " + message;
//     // }

// }
