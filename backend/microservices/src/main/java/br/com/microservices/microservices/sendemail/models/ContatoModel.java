package br.com.microservices.microservices.sendemail.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ContatoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    int numeroDoContato;
    String titulo;
    String textoConteudo;
    int review;

    public ContatoModel(String titulo, String textoConteudo, int review) {
        this.review = review;
        this.titulo = titulo;
        this.textoConteudo = textoConteudo;
    }

}
