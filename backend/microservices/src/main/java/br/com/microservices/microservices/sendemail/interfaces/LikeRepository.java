package br.com.microservices.microservices.sendemail.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.microservices.microservices.sendemail.models.LikeModel;

@Repository
public interface LikeRepository extends JpaRepository<LikeModel, Integer> {

}