package br.com.microservices.microservices.loja.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.com.microservices.microservices.authentication.model.Usuario;
import br.com.microservices.microservices.servico.models.Servico;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comercio {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    Usuario donoDaLoja;

    @Column(unique = true)
    String nomeLoja;
    String enderecoLoja;

    @JsonManagedReference
    @OneToMany(mappedBy = "comercio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Disponibilidade> disponibilidades;

    @JsonManagedReference
    @OneToMany(mappedBy = "comercios")
    private List<Servico> servicos = new ArrayList<>();

    public List<Disponibilidade> getDisponibilidades(LocalDate data) {
        return disponibilidades.stream()
                .filter(disponibilidade -> disponibilidade.getData().equals(data))
                .collect(Collectors.toList());
    }

}
