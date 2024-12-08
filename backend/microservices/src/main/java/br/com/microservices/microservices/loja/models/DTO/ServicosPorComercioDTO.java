package br.com.microservices.microservices.loja.models.DTO;

import java.time.LocalTime;

public record ServicosPorComercioDTO(

        String email,
        String nomeLoja,
        String nomeServico,
        Double valorServico,
        LocalTime tempoServico,
        String descricao) {

}
