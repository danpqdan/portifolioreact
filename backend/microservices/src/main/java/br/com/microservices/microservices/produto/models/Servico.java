package br.com.microservices.microservices.produto.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Servico")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "nomeServico")
public class Servico {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    int id;
    String nomeServico;
    Double valorServico;
    Long tempoServico;

    public Servico(String nomeServico, Double valorServico, Long tempoServico) {
        this.nomeServico = nomeServico;
        this.valorServico = valorServico;
        this.tempoServico = tempoServico;
    }

}
