package br.com.microservices.microservices.authentication.model;

import jakarta.validation.constraints.Email;
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
public class AuthenticationDTO {
    @NotNull(message = "Email não pode ser nulo.")
    @NotEmpty(message = "Email não pode ser vazio.")
    @Email(message = "Email inválido.")
    String email;

    @NotNull(message = "Username não pode ser nulo.")
    @NotEmpty(message = "Username não pode ser vazio.")
    @Size(min = 3, max = 50, message = "Username deve ter entre 3 e 50 caracteres.")
    String username;

    @NotNull(message = "Password não pode ser nulo.")
    @NotEmpty(message = "Password não pode ser vazio.")
    @Size(min = 6, max = 100, message = "Password deve ter entre 6 e 100 caracteres.")
    String password;
}
