package br.com.microservices.microservices.loja.models.DTO;

import java.time.LocalDate;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

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