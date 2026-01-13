package med.voll.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
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

@Configuration  //serve para indicar que essa classe é uma classe de configuração do Spring
@EnableWebSecurity //serve para indicar que essa classe é uma configuração de segurança web do Spring
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean //serve para indicar que esse método retorna um bean gerenciado pelo Spring
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Desabilita proteção CSRF pois usaremos JWT (stateless)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Configura sessão como STATELESS para JWT
                .authorizeHttpRequests(req -> {
                    req.requestMatchers(HttpMethod.POST, "/login").permitAll(); // Libera apenas POST /login
                    req.anyRequest().authenticated(); // Todas as outras rotas precisam de autenticação
                })
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class) // Adiciona nosso filtro customizado
                .build();
    }

    @Bean  //ensina ao Spring como criar um objeto AuthenticationManager que será injetado
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        // Configura o AuthenticationManager que será usado para autenticação
        return configuration.getAuthenticationManager(); // retorna o AuthenticationManager configurado pelo Spring
    }

    @Bean  //configura o PasswordEncoder para criptografar senhas
    public PasswordEncoder passwordEncoder() {
        // Retorna o BCryptPasswordEncoder que é usado para criptografar e validar senhas
        return new BCryptPasswordEncoder(); // Mesmo algoritmo usado no hash da senha no banco
    }

}