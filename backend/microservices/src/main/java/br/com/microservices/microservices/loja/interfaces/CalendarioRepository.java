package br.com.microservices.microservices.loja.interfaces;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.microservices.microservices.loja.models.Disponibilidade;

@Repository
public interface CalendarioRepository extends JpaRepository<Disponibilidade, Long> {
    List<Disponibilidade> findByComercioId(UUID comercioId);

    List<Disponibilidade> findByDataAndComercioId(LocalDate data, UUID comercioId);
}
