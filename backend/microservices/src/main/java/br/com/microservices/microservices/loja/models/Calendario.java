package br.com.microservices.microservices.loja.models;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.com.microservices.microservices.loja.models.DTO.DiasDeFolgaEnum;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Calendario {

    @JsonBackReference
    Disponibilidade disponibilidade;

    @ElementCollection(targetClass = DiasDeFolgaEnum.class)
    @CollectionTable(name = "dias_de_folga", joinColumns = @JoinColumn(name = "disponibilidade_id"))
    @Enumerated(EnumType.STRING)
    private Set<DiasDeFolgaEnum> diasDeFolga;

    @ElementCollection
    @CollectionTable(name = "dias_de_folga_exceptions", joinColumns = @JoinColumn(name = "disponibilidade_id"))
    private Set<LocalDate> diasDeFolgaExceptions;

    private float horarioTotalDeFuncionamento;

    public void setDiaEspecificoDeFolga(LocalDate diaDeFolga) {
        diasDeFolgaExceptions.add(diaDeFolga);
    }

    public Set<LocalDate> atualizarDiasIndisponiveis(Set<DiasDeFolgaEnum> folgaEnum,
            Set<LocalDate> diasDeFolgaExcpetion) {
        LocalDate diaDeInicio = LocalDate.now();
        LocalDate diaFinal = diaDeInicio.plusDays(360);
        Set<LocalDate> diasAtualizados = new HashSet<>();

        for (LocalDate dia = diaDeInicio; !dia.isAfter(diaFinal); dia = dia.plusDays(1)) {
            final LocalDate diaAtual = dia;
            if (folgaEnum.stream()
                    .anyMatch(folga -> folga.name().equalsIgnoreCase(diaAtual.getDayOfWeek().name()))) {
                diasAtualizados.add(dia);
            }
        }
        diasAtualizados.addAll(diasDeFolgaExcpetion);

        return diasAtualizados;
    }

}
