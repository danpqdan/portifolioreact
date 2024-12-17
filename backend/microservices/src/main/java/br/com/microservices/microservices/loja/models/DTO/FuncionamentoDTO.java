package br.com.microservices.microservices.loja.models.DTO;

import java.time.LocalDate;
import java.util.Set;

import br.com.microservices.microservices.authentication.model.UsuarioDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class FuncionamentoDTO {
        @NotNull
        UsuarioDTO usuario;
        @NotNull
        String nomeLoja;

        Set<LocalDate> diaDeFolga;
        FomarmatterHorarioDTO horarioDeAbertura;
        FomarmatterHorarioDTO horarioDeFechamento;
        Set<DiasDeFolgaEnum> diasFechado;

}
