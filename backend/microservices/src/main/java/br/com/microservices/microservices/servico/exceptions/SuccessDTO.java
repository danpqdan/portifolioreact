package br.com.microservices.microservices.servico.exceptions;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SuccessDTO {
    private LocalDateTime timestamp;
    private int status;
    private String message;
    private String path;
    private Object data; // Dados adicionais, se houver
}
