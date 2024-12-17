package br.com.microservices.microservices.loja.models.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuscaDisponibilidadeDTO {
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

        public LocalDate toLocalDate() {
                try {
                        return LocalDate.of(year, mes, day);
                } catch (Exception e) {
                        throw new IllegalArgumentException("Data inválida: " + e.getMessage());
                }
        }

        

}