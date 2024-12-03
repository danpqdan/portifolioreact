package br.com.microservices.microservices.sendemail.models;

public record ContatoDTO(
        String subject,
        String body,
        int review) {

}
