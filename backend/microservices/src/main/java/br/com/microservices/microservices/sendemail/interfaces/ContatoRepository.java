package br.com.microservices.microservices.sendemail.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.microservices.microservices.sendemail.models.ContatoModel;

@Repository
public interface ContatoRepository extends JpaRepository<ContatoModel, Integer> {

}
