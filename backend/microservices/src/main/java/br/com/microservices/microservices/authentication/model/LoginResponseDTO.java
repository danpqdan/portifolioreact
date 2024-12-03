package br.com.microservices.microservices.authentication.model;

public record LoginResponseDTO(String token, String role, String username) {

}
