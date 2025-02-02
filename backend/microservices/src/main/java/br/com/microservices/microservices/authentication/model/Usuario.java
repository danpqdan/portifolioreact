package br.com.microservices.microservices.authentication.model;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.com.microservices.microservices.loja.models.Comercio;
import br.com.microservices.microservices.servico.models.ServicoAgendado;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "usuarios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "username")
@ToString
public class Usuario implements UserDetails {

    @Id
    @Column(unique = true)
    private String email;
    private String username;
    private String password;
    private boolean emailConfirmed = false;
    @OneToOne
    @JoinColumn(name = "comercio_id")
    @JsonBackReference
    private Comercio comercio;
    @Enumerated(EnumType.STRING)
    Set<Roles> role;
    @OneToMany
    @JsonManagedReference
    List<ServicoAgendado> servico;

    public void setServico(ServicoAgendado servicoAgendado) {
        this.servico.add(servicoAgendado);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.stream()
                .flatMap(r -> {
                    if (r == Roles.DONO) {
                        return Stream.of(
                                new SimpleGrantedAuthority("ROLE_DONO"),
                                new SimpleGrantedAuthority("ROLE_FUNCIONARIO"),
                                new SimpleGrantedAuthority("ROLE_USUARIO"));
                    }
                    if (r == Roles.FUNCIONARIO) {
                        return Stream.of(
                                new SimpleGrantedAuthority("ROLE_FUNCIONARIO"),
                                new SimpleGrantedAuthority("ROLE_USUARIO"));
                    }
                    if (r == Roles.USUARIO) {
                        return Stream.of(
                                new SimpleGrantedAuthority("ROLE_USUARIO"));
                    } else {
                        return null;
                    }
                })
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.emailConfirmed;
    }

    public Usuario(UsuarioDTO usuarioDTO) {
        this.email = usuarioDTO.email;
        this.password = usuarioDTO.password;
        this.username = usuarioDTO.username;
    }
}
