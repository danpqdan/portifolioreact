package br.com.microservices.microservices.sendemail.models;

import java.util.regex.Pattern;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ContatoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    int numeroDoContato;
    private String remetente;
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

    public ContatoModel(String titulo, String body, String destinatario) {
        this.destinatario = destinatario;
        this.titulo = titulo;
        this.body = body;
    }

    public ContatoModel(String titulo, String body, int review) {
        this.review = review;
        this.titulo = titulo;
        this.body = body;
    }

    public boolean validarEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(email).matches();
    }

    public String generateHtmlEmail(String token, String email) {
        this.body = "<!DOCTYPE html>" +
                "<html>" +
                "<head><title>Confirmação de Conta</title></head>" +
                "<body>" +
                "<h1>Bem-vindo!</h1>" +
                "<p>Obrigado por se cadastrar.</p>" +
                "<p>Seu token de confirmação é:</p>" +
                "<p><strong>" + token + "</strong></p>" +
                "<p>Use o token para validar seu cadastro ou <a href='http://localhost:8080/auth/singup/" + token
                + "'>clique aqui</a>.</p>"
                + "'>clique aqui</a>.</p>" +
                "<p>Login: " + email + "</p>" +
                "<p>Atenciosamente,<br>Equipe de softwareDev</p>" +
                "</body>" +
                "</html>";

        return this.body;
    }

    public String generateHtmlForReview(String tittle, int review, String body) {
        this.body = "<!DOCTYPE html>" +
                "<html>" +
                "<head><title>" + tittle + "</title></head>" +
                "<body style='font-family: Arial, sans-serif;border: 1px solid black; border-radius:12px ; background-color:rgb(117, 119, 121); color: #333; text-align: center;'>"
                +
                "<h1 style='color:rgb(0, 0, 0);'>Obrigado por avaliar!</h1>" +
                "<h3 style='font-size: 18px;'>Esse foi o conteúdo descrito:</h3>" +
                "<p style='font-size: 16px; line-height: 1.5;'>" + body + "</p>" +
                "<p style='font-weight: bold; color:rgb(0, 0, 0);'>Nota: " + review
                + " de 5 (máximo). Obrigado por avaliar, estarei melhorando sempre!</p>" +
                "</body>" +
                "</html>";

        return this.body;
    }

}
