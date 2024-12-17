package br.com.microservices.microservices.loja.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.com.microservices.microservices.authentication.model.Usuario;
import br.com.microservices.microservices.servico.models.Servico;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
    @Column(unique = true)
    String nomeLoja;

    @ManyToOne
    @JoinColumn(name = "dono_da_loja_id", nullable = false)
    @JsonManagedReference
    Usuario donoDaLoja;

    String enderecoLoja;

    @JsonManagedReference
    @OneToOne
    private Disponibilidade disponibilidades;

    @JsonManagedReference
    @OneToMany(mappedBy = "comercios")
    private List<Servico> servicosDaLoja = new ArrayList<>();

}
