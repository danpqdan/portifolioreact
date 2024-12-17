package br.com.microservices.microservices.servico.models;

import java.time.LocalTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.microservices.microservices.loja.models.Comercio;
import br.com.microservices.microservices.loja.models.Disponibilidade;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "ServicoLojista")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Inheritance(strategy = InheritanceType.JOINED)
public class Servico {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    String nomeServico;
    Double valorServico;
    Long tempoServico;
    String descricao;

    @JsonBackReference
    @ManyToOne
    private Comercio comercios;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "disponibilidade_id")
    private Disponibilidade disponibilidade;

    public Servico(String nomeServico, Double valorServico, Long tempoServico) {
        this.nomeServico = nomeServico;
        this.valorServico = valorServico;
        this.tempoServico = tempoServico;
    }

    public Servico(String nomeServico, Double valorServico, Long tempoServico, String descricao) {
        this.nomeServico = nomeServico;
        this.valorServico = valorServico;
        this.tempoServico = tempoServico;
        this.descricao = descricao;
    }

    // public void setTempoServico(Long tempo) {
    // LocalTime tempoServicoLocalTime = LocalTime.MIN.plusMinutes(tempo);
    // this.tempoServico = tempoServicoLocalTime;
    // }

}
