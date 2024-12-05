package br.com.microservices.microservices.servico.models;

public record ServicoDTO(
        String nomeServico,
        Double valorServico,
        Long tempoServico) {

}
