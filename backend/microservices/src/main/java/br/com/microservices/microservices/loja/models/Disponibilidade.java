package br.com.microservices.microservices.loja.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Disponibilidade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idDisponibilidade;

    @ManyToOne
    @JoinColumn(name = "comercio_id", nullable = false)
    @JsonBackReference
    private Comercio comercio;

    private LocalTime horarioAbertura;
    private LocalTime horarioFechamento;

    @ElementCollection
    @CollectionTable(name = "horario_de_funcionamento", joinColumns = @JoinColumn(name = "disponibilidade_id"))
    private Set<LocalDateTime> horarioAgendadoInicio = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "horario_de_funcionamento", joinColumns = @JoinColumn(name = "disponibilidade_id"))
    private Set<LocalDateTime> horarioAgendadoFim = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "dias_indisponiveis", joinColumns = @JoinColumn(name = "disponibilidade_id"))
    @Column(name = "diasIndisponiveis")
    private Set<LocalDate> calendarioDeNaoFuncionamento = new HashSet<>();

    public void setCalendarioDeFuncionamento(Set<LocalDate> calendarioDeFuncionamento) {
        this.calendarioDeNaoFuncionamento = calendarioDeFuncionamento;
    }

    public Set<LocalDateTime> getHorariosAgendados() {
        // Combinar todos os horários de início e fim em uma lista única
        Set<LocalDateTime> todosHorarios = new HashSet<>();
        todosHorarios.addAll(horarioAgendadoInicio); // Adiciona horários de início
        todosHorarios.addAll(horarioAgendadoFim);
        return todosHorarios;
    }

    public boolean verificarDiaDisponivel(LocalDate data) {
        return !calendarioDeNaoFuncionamento.contains(data);
    }

    public void setHorarioAgendado(LocalDateTime inicio, LocalDateTime fim) {
        if (inicio.toLocalDate().isEqual(fim.toLocalDate()) && inicio.toLocalTime().isBefore(fim.toLocalTime())) {
            this.horarioAgendadoInicio.add(inicio);
            this.horarioAgendadoFim.add(fim);
        }
    }

    public boolean isDentroDoHorarioDisponivel(LocalTime inicio, LocalTime fim) {
        LocalTime horarioInicioDisponivel = this.horarioAbertura;
        LocalTime horarioFimDisponivel = this.horarioFechamento;

        return !inicio.isBefore(horarioInicioDisponivel) && !fim.isAfter(horarioFimDisponivel);
    }

}
