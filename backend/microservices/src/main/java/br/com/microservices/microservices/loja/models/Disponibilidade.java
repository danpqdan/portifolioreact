package br.com.microservices.microservices.loja.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.com.microservices.microservices.servico.models.ServicoAgendado;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
    @Column(name = "horariosDisponiveisNoDia")
    private Set<LocalTime> horariosDisponiveisNoDia = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "dias_de_funcionamento", joinColumns = @JoinColumn(name = "disponibilidade_id"))
    @Column(name = "diaDeFuncionamento")
    private Set<LocalDate> calendarioDeNaoFuncionamento = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @CollectionTable(name = "servicos_agendados", joinColumns = @JoinColumn(name = "disponibilidade_id"))
    private List<ServicoAgendado> servicosNaAgenda = new ArrayList<>();

    public void setCalendarioDeFuncionamento(Set<LocalDate> calendarioDeFuncionamento) {
        this.calendarioDeNaoFuncionamento = calendarioDeFuncionamento;
    }

    public boolean verificarDiaDisponivel(LocalDate data) {
        return !calendarioDeNaoFuncionamento.contains(data);
    }

    public List<ServicoAgendado> setServicoNaAgenda(ServicoAgendado servico) {
        LocalTime inicio = servico.getHoraDoInicio();
        LocalTime fim = servico.getHoraDoFinal();
        LocalDate dia = servico.getDiaDoSerivoco();
        if (!calendarioDeNaoFuncionamento.contains(dia)) {
            throw new IllegalArgumentException("Dia fora do calendário de funcionamento.");
        }
        boolean conflito = servicosNaAgenda.stream()
                .filter(s -> s.getDiaDoSerivoco().equals(servico.getDiaDoSerivoco()))
                .anyMatch(s -> {
                    LocalTime inicioExistente = s.getHoraDoInicio();
                    LocalTime fimExistente = s.getHoraDoFinal();

                    return (inicio.isBefore(inicioExistente) && fim.isAfter(fimExistente));
                });

        if (conflito) {
            throw new IllegalArgumentException("Horário já ocupado por outro serviço.");
        }
        servicosNaAgenda.add((ServicoAgendado) servico);
        horariosDisponiveisNoDia.removeIf(horario -> !horario.isBefore(inicio) && !horario.isAfter(fim));
        return servicosNaAgenda;

    }

}
