package br.com.microservices.microservices.loja.models.DTO;

import java.time.LocalDate;
import java.util.Set;

import br.com.microservices.microservices.authentication.model.UsuarioDTO;
import lombok.Getter;

@Getter
public class FuncionamentoDTO {
        UsuarioDTO usuario;
        String nomeLoja;
        FuncionamentoHorarioDTO horarioDeAbertura;
        FuncionamentoHorarioDTO horarioDeFechamento;
        Set<DiasDeFolgaEnum> diasDeFolga;

}
