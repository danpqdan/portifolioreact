package br.com.microservices.microservices.loja.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DiasDeFolgaEnum {
    MONDAY("segunda-feira"),
    TUESDAY("terça-feira"),
    WEDNESDAY("quarta-feira"),
    THURSDAY("quinta-feira"),
    FRIDAY("sexta-feira"),
    SATURDAY("sábado"),
    SUNDAY("domingo");

    private final String descricao;
}
