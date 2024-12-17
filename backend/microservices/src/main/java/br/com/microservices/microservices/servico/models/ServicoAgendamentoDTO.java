package br.com.microservices.microservices.servico.models;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ServicoAgendamentoDTO {

    @NotNull
    @Min(1)
    @Max(31)
    private Integer day;

    @NotNull
    @Min(1)
    @Max(12)
    private Integer mes;

    @NotNull
    @Min(1900)
    private Integer year;

    @NotNull
    @Min(0)
    @Max(23)
    private Integer hour;

    @NotNull
    @Min(0)
    @Max(59)
    private Integer minute;

    public LocalTime toLocalTime() {
        return LocalTime.of(hour, minute);
    }

    public LocalDate toLocalDate() {
        try {
            return LocalDate.of(year, mes, day);
        } catch (Exception e) {
            throw new IllegalArgumentException("Data inválida: " + e.getMessage());
        }
    }

}
