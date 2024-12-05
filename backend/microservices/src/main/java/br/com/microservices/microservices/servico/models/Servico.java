package br.com.microservices.microservices.servico.models;

import java.sql.Time;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.com.microservices.microservices.loja.models.Comercio;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Servico")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Servico {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    String nomeServico;
    Double valorServico;
    Time tempoServico;
    String descricao;
    @JsonBackReference
    @ManyToOne
    private Comercio comercios;

    public Servico(String nomeServico, Double valorServico, Time tempoServico) {
        this.nomeServico = nomeServico;
        this.valorServico = valorServico;
        this.tempoServico = tempoServico;
    }

}
