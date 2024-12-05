package br.com.microservices.microservices.loja.models;

import java.sql.Time;

public record ServicosPorComercio(

                String email,
                String nomeLoja,
                String nomeServico,
                Double valorServico,
                Time tempoServico,
                String descricao) {

}
