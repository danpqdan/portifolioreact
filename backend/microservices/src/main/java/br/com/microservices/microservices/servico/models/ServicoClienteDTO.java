package br.com.microservices.microservices.servico.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import br.com.microservices.microservices.authentication.model.UsuarioDTO;
import lombok.Getter;

@Getter
public class ServicoClienteDTO {

    UsuarioDTO usuarioDTO;
    ServicoAgendamentoDTO agendamentoDTO;
    String nomeDaLoja;
    UUID servicoId;

}