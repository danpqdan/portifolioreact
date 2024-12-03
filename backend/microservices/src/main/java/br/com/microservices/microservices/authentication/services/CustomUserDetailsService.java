package br.com.microservices.microservices.authentication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.microservices.microservices.authentication.interfaces.UsuarioRepository;
import br.com.microservices.microservices.authentication.model.AuthenticationDTO;
import br.com.microservices.microservices.authentication.model.LoginResponseDTO;
import br.com.microservices.microservices.authentication.model.Roles;
import br.com.microservices.microservices.authentication.model.Usuario;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    TokenService tokenService;
    @Autowired
    SecurityFilter securityFilter;
    @Lazy
    @Autowired
    private AuthenticationManager authenticationManager;

    public LoginResponseDTO loginDeUsuario(AuthenticationDTO login) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generatedToken((Usuario) auth.getPrincipal());
        var tokeRole = tokenService.extractUserRoles(token);
        return new LoginResponseDTO(token, tokeRole, login.getUsername());

    }

    public String novoUsuario(AuthenticationDTO login) {
        if (usuarioRepository.findByUsername(login.getUsername()) != null)
            return "Usuario ja existe";

        String encryptedPassword = new BCryptPasswordEncoder().encode(login.getPassword());
        Usuario novoUsuario = new Usuario();
        novoUsuario.setPassword(encryptedPassword);
        novoUsuario.setUsername(login.getUsername());
        novoUsuario.setRole(Roles.USER);

        usuarioRepository.save(novoUsuario);
        // sacolaRepository.save(sacola);
        return "Usuario registrado";
    }

    public String transformAdminRole(AuthenticationDTO login) {
        Usuario usuario = usuarioRepository.encontrarByUsername(login.getUsername());
        usuario.setRole(Roles.ADMIN);
        usuarioRepository.save(usuario);
        return "Usuario atualizado";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByUsername(username);
    }

    public UserDetails validarUsuarioPorToken(String token, LoginResponseDTO login) {
        var userToken = tokenService.extrairUsuario(token);
        var usuario = validarUsuario(login);
        if (userToken.equals(usuario.getUsername())) {
            var user = loadUserByUsername(login.username());
            return user;
        }
        return null;
    }

    public Usuario validarUsuario(LoginResponseDTO login)
            throws UsernameNotFoundException {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        var usuario = usuarioRepository.findByUsername(login.username());
        if (usuario.getUsername().equals(login.username())
                && passwordEncoder.matches(login.username(), usuario.getPassword())) {
            return usuarioRepository.encontrarByUsername(login.username());
        }
        return null;
    }

    public boolean testeUsuario(LoginResponseDTO login) {
        boolean teste = true;
        var a = validarUsuario(login);
        if (a == null) {
            return teste = false;
        }
        return teste;

    }

}
