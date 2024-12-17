package br.com.microservices.microservices.servico.models;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.com.microservices.microservices.authentication.model.Usuario;
import jakarta.persistence.Entity;
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
public class ServicoAgendado extends Servico {
    @ManyToOne
    @JoinColumn(name = "servico_id")
    @JsonBackReference
    private Servico servico;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonBackReference
    private Usuario usuario;

    LocalDate diaDoSerivoco;
    LocalTime horaDoInicio;
    LocalTime horaDoFinal;
}
