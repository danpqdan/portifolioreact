package br.com.microservices.microservices.authentication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.microservices.microservices.authentication.interfaces.UsuarioRepository;
import br.com.microservices.microservices.authentication.model.AuthenticationDTO;
import br.com.microservices.microservices.authentication.model.LoginResponseDTO;
import br.com.microservices.microservices.authentication.model.Roles;
import br.com.microservices.microservices.authentication.model.Usuario;
import br.com.microservices.microservices.kafka.service.UsuarioProducerService;
import br.com.microservices.microservices.loja.interfaces.ComercioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UsuarioProducerService usuarioProducerService;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    ComercioRepository comercioRepository;
    @Autowired
    TokenService tokenService;
    @Autowired
    SecurityFilter securityFilter;
    @Lazy
    @Autowired
    private AuthenticationManager authenticationManager;

    public LoginResponseDTO loginDeUsuario(AuthenticationDTO login) {
        Usuario usuario = usuarioRepository.encontrarByEmail(login.getEmail());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(login.getPassword(), usuario.getPassword()) || !usuario.isEmailConfirmed() || !login.getEmail().equalsIgnoreCase(usuario.getEmail())) {
            throw new BadCredentialsException("Dados invalidos ou email não confirmado!");
        }
        var usernamePassword = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generatedToken((Usuario) auth.getPrincipal());
        var tokeRole = tokenService.extractUserRoles(token);
        return new LoginResponseDTO(token, tokeRole, login.getUsername());

    }

    public Usuario activateUserByToken(String token) {
        var usuarioPorToken = tokenService.extrairUsuario(token);
        Usuario usuario = usuarioRepository.encontrarByUsername(usuarioPorToken);
        usuario.setEmailConfirmed(true);
        return usuarioRepository.save(usuario);
    }

    public Usuario novoUsuario(AuthenticationDTO login) throws JsonProcessingException {
        var user = usuarioRepository.encontrarByUsername(login.getUsername());
        if (user != null) {
            throw new BadCredentialsException("As credenciais fornecidas já existem ou são invalidas");
        } else {
            Usuario novoUsuario = new Usuario();
            String newPassword = new BCryptPasswordEncoder().encode(login.getPassword());
            novoUsuario.setEmail(login.getEmail());
            novoUsuario.setPassword(newPassword);
            novoUsuario.setUsername(login.getUsername());
            novoUsuario.setRole(Roles.USER);
            usuarioProducerService.sendUsuario(novoUsuario);
            usuarioProducerService.sendEmailNovoCliente(novoUsuario);
            return novoUsuario;
        }
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
