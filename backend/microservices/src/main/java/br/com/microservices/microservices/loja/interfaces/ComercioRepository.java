package br.com.microservices.microservices.loja.interfaces;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.microservices.microservices.loja.models.Comercio;

@Repository
public interface ComercioRepository extends JpaRepository<Comercio, String> {
    Optional<Comercio> findByNomeLoja(String nomeLoja);

}