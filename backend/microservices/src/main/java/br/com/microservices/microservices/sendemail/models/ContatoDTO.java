package br.com.microservices.microservices.sendemail.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContatoDTO {

        private String remetente;

        @NotNull(message = "O destinatário não pode ser nulo.")
        @NotEmpty(message = "O destinatário não pode ser vazio.")
        @Email(message = "O destinatário deve ser um e-mail válido.")
        private String destinatario;

        @NotNull(message = "O corpo da mensagem não pode ser nulo.")
        @NotEmpty(message = "O corpo da mensagem não pode ser vazio.")
        @Size(min = 5, max = 500, message = "O corpo da mensagem deve ter entre 5 e 500 caracteres.")
        private String body;

        @NotNull(message = "O corpo da mensagem não pode ser nulo.")
        @NotEmpty(message = "O corpo da mensagem não pode ser vazio.")
        @Size(min = 5, max = 500, message = "O corpo da mensagem deve ter entre 5 e 500 caracteres.")
        private String titulo;

        @Min(value = 1, message = "O review deve ser no mínimo 1.")
        @Max(value = 5, message = "O review deve ser no máximo 5.")
        int review;
}
