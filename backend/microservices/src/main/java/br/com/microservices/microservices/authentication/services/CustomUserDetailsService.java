package br.com.microservices.microservices.authentication.services;

import java.util.Set;

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
import br.com.microservices.microservices.authentication.model.LoginResponseDTO;
import br.com.microservices.microservices.authentication.model.Roles;
import br.com.microservices.microservices.authentication.model.Usuario;
import br.com.microservices.microservices.authentication.model.UsuarioDTO;
import br.com.microservices.microservices.loja.interfaces.ComercioRepository;
import br.com.microservices.microservices.sendemail.services.EmailService;
import jakarta.transaction.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    EmailService emailService;
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

    public LoginResponseDTO loginDeUsuario(UsuarioDTO login) {
        Usuario usuario = usuarioRepository.encontrarByEmail(login.getEmail());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(login.getPassword(), usuario.getPassword()) || !usuario.isEmailConfirmed()
                || !login.getEmail().equalsIgnoreCase(usuario.getEmail())) {
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

    @Transactional
    public Usuario novoUsuario(UsuarioDTO login) {
        var user = usuarioRepository.encontrarByUsername(login.getUsername());
        if (user != null) {
            throw new BadCredentialsException("As credenciais fornecidas já existem ou são invalidas");
        } else {
            Usuario novoUsuario = new Usuario();
            String newPassword = new BCryptPasswordEncoder().encode(login.getPassword());
            novoUsuario.setEmail(login.getEmail());
            novoUsuario.setPassword(newPassword);
            novoUsuario.setUsername(login.getUsername());
            novoUsuario.setRole(Set.of(Roles.USUARIO));
            usuarioRepository.save(novoUsuario);
            return novoUsuario;
        }
    }

    public String transformAdminRole(UsuarioDTO login) {
        Usuario usuario = usuarioRepository.encontrarByUsername(login.getUsername());
        usuario.setRole(Set.of(Roles.DONO));
        usuarioRepository.save(usuario);
        return "Usuario atualizado";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByUsername(username);
    }

    public Usuario validarUsuario(UsuarioDTO login, String token) {
        try {
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7); // Remove "Bearer " (7 caracteres)
            }
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            var usuarioToken = tokenService.extrairUsuario(token);
            var usuarioOptional = usuarioRepository.findById(login.getEmail());
            if (usuarioOptional.isPresent()) {
                var usuario = usuarioOptional.get();
                if (usuarioToken.equals(usuario.getUsername())
                        && passwordEncoder.matches(login.getPassword(), usuario.getPassword())) {
                    return usuario;
                } else {
                    throw new BadCredentialsException("Usuário ou senha inválidos.");
                }
            } else {
                throw new BadCredentialsException("Usuário não encontrado.");
            }
        } catch (BadCredentialsException e) {
            throw e;
        }
    }
}
