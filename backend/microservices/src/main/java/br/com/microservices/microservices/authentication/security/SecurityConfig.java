package br.com.microservices.microservices.authentication.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import br.com.microservices.microservices.authentication.services.SecurityFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    SecurityFilter securityFilter;

    @Value("${api.security.route.prod}")
    private String secretProd;
    @Value("${api.security.route.dev}")
    private String secretDev;
    @Value("${api.security.route.kafka}")
    private String kafkaRoute;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(request -> {
                    var corsConfiguration = new CorsConfiguration();
                    corsConfiguration.addAllowedOriginPattern(secretProd);
                    corsConfiguration.addAllowedOriginPattern(secretDev);
                    corsConfiguration.addAllowedHeader("*");
                    corsConfiguration.addAllowedMethod("*");
                    corsConfiguration.setAllowCredentials(true);

                    return corsConfiguration;
                }))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/singup/usuario").permitAll()
                        .requestMatchers("/auth/singup/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/comercio/criar").permitAll()
                        .requestMatchers(HttpMethod.POST, "/comercio/**").hasRole("DONO")

                        .requestMatchers(HttpMethod.POST, "/servicos").hasRole("DONO")
                        .requestMatchers(HttpMethod.POST, "/servicos/**").hasRole("DONO")
                        .requestMatchers(HttpMethod.GET, "/servicos/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/like").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/like").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/review").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/servicos").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/servicos").permitAll()

                        .requestMatchers("swagger-ui/**", "/v3/api-docs/**", "/doc").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}