package br.com.microservices.microservices.loja.models.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HorarioDisponibilidadeDTO {
    LocalDateTime horarioAgendadoInicio;
    LocalDateTime horarioAgendadoFim;

}
