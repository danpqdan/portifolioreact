package br.com.microservices.microservices.loja.models.DTO;

import java.sql.Time;
import java.util.Date;

public record ComercioDTO(
        String username,
        String password,
        String email,
        String nomeLoja,
        String endereco
        ) {

}
