package br.com.microservices.microservices.servico.interfaces;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.microservices.microservices.servico.models.ServicoAgendado;

@Repository
public interface ServicoAgendadoRepository extends JpaRepository<ServicoAgendado, UUID>{
    
}
