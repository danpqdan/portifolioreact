/**
 * This Java class initializes an admin user if it doesn't already exist in the system.
 */
// package br.com.microservices.microservices.authentication.controller;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.transaction.annotation.Transactional;

// import br.com.microservices.microservices.authentication.interfaces.UsuarioRepository;
// import br.com.microservices.microservices.authentication.model.AuthenticationDTO;
// import br.com.microservices.microservices.authentication.services.CustomUserDetailsService;

// @Configuration
// public class AdminUserInitializer implements CommandLineRunner {

//     @Value("${admin.username}")
//     private String adminUsername;

//     @Value("${admin.password}")
//     private String adminPassword;

//     private final UsuarioRepository userRepository;
//     private final PasswordEncoder passwordEncoder;
//     private final CustomUserDetailsService userDetailsService;

//     public AdminUserInitializer(UsuarioRepository userRepository, PasswordEncoder passwordEncoder,
//             CustomUserDetailsService userDetailsService) {
//         this.userRepository = userRepository;
//         this.passwordEncoder = passwordEncoder;
//         this.userDetailsService = userDetailsService;
//     }

//     @Override
//     @Transactional
//     public void run(String... args) throws Exception {
//         if (userRepository.encontrarByUsername(adminUsername) == null) {
//             userDetailsService.novoUsuario(new AuthenticationDTO(adminUsername, adminPassword));
//             userDetailsService.transformAdminRole(new AuthenticationDTO(adminUsername, adminPassword));
//             System.out.println("Usuário administrador criado com sucesso!");
//         } else {
//             System.out.println("Usuário administrador já existe.");
//         }
//     };
// }
