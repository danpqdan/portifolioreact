package br.com.microservices.microservices.authentication.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import br.com.microservices.microservices.authentication.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    UserDetails findByUsername(String username);

    @Query(value = "SELECT * FROM usuarios u WHERE u.username = :username", nativeQuery = true)
    Usuario encontrarByUsername(@Param("username") String username);

    // Optional<Usuario> loadByUsername(String username);

}
