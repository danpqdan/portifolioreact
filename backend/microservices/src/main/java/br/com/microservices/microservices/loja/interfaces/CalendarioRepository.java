package br.com.microservices.microservices.loja.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.microservices.microservices.loja.models.DiaEHorarioDeFuncionamento;

@Repository
public interface CalendarioRepository extends JpaRepository<DiaEHorarioDeFuncionamento, Long> {

}
