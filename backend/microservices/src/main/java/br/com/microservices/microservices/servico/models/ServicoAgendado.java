package br.com.microservices.microservices.servico.models;

import java.time.LocalDateTime;

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
    private Servico servico;

    LocalDateTime diaHoraDoAgendamentoInicio;
    LocalDateTime diaHoraDoAgendamentoFinal;
}
