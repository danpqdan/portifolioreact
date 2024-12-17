package br.com.microservices.microservices.loja.interfaces;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.microservices.microservices.loja.models.Disponibilidade;

@Repository
public interface DisponibilidadeRepository extends JpaRepository<Disponibilidade, UUID> {

    Disponibilidade findByComercio_NomeLoja(String nomeDoComercio);

}
