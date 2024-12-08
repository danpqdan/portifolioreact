package br.com.microservices.microservices.loja.models.DTO;

import java.time.LocalTime;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class FuncionamentoHorarioDTO {

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

}
