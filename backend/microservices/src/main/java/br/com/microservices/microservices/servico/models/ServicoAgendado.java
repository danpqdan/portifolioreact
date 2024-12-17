package br.com.microservices.microservices.servico.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.com.microservices.microservices.authentication.model.Usuario;
import br.com.microservices.microservices.loja.models.Disponibilidade;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ServicoAgendado")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServicoAgendado {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "servico_id")
    @JsonBackReference
    private Servico servico;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonBackReference
    private Usuario usuario;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "disponibilidade_id")
    @JsonBackReference
    Disponibilidade disponibilidade;

    LocalDate diaDoSerivoco;
    LocalTime horaDoInicio;
    LocalTime horaDoFinal;

    public ServicoAgendado(Servico servico1) {
        this.servico = servico1;
    }

}
