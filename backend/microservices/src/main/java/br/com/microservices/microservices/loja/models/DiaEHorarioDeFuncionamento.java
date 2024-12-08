package br.com.microservices.microservices.loja.models;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.com.microservices.microservices.loja.models.DTO.DiasDeFolgaEnum;
import br.com.microservices.microservices.servico.models.Servico;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class DiaEHorarioDeFuncionamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Set<LocalDate> diasDeFuncionamento;
    @ElementCollection(targetClass = DiasDeFolgaEnum.class)
    @Enumerated(EnumType.STRING)
    Set<DiasDeFolgaEnum> diasDeFolga;
    Set<LocalDate> diasDeFolgaExceptions;
    LocalTime horarioAbertura;
    LocalTime horarioFechamento;
    float horarioTotalDeFuncionamento;
    @ManyToOne
    @JoinColumn(name = "comercio_id", nullable = false)
    @JsonBackReference
    Comercio comercio;
    @OneToMany(mappedBy = "disponibilidade", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Servico> servicosNaAgenda = new ArrayList<>();

    public void setHorarioTotalDeFuncionamento(LocalTime horarioAbertura, LocalTime horarioFechamento) {
        Duration duracao = Duration.between(horarioAbertura, horarioFechamento);
        long totalMinutos = duracao.toMinutes();
        this.horarioTotalDeFuncionamento = (float) totalMinutos / 60;
    }

    public List<LocalDate> diasDeFuncionamento(Set<DiasDeFolgaEnum> diasDeFolga, Year ano) {
        List<LocalDate> diasFuncionamento = new ArrayList<>();

        LocalDate primeiroDia = ano.atDay(1);
        LocalDate ultimoDia = ano.atDay(ano.length());

        for (LocalDate dia = primeiroDia; !dia.isAfter(ultimoDia); dia = dia.plusDays(1)) {
            if (diasDeFolga.contains(DiasDeFolgaEnum.valueOf(dia.getDayOfWeek().name()))) {
                diasFuncionamento.add(dia);
            }
        }
        return diasFuncionamento;
    }

    public void setDiaEspecificoDeFolga(LocalDate diaDeFolga) {
        diasDeFolgaExceptions.add(diaDeFolga);
    }

    // public List<?> servicosNoDiaSelecionado(YearMonth dia){

    // }

}
