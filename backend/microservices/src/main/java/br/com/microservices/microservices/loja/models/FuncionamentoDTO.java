package br.com.microservices.microservices.loja.models;

import java.time.LocalDate;
import java.time.LocalTime;

public record FuncionamentoDTO(
        String nomeLoja,
        LocalDate data,
        LocalTime horarioDeAbertura,
        LocalTime horarioDeFechamento

) {

}
