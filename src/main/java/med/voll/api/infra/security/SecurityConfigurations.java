package med.voll.api.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration  //serve para indicar que essa classe é uma classe de configuração do Spring
@EnableWebSecurity //serve para indicar que essa classe é uma configuração de segurança web do Spring
public class SecurityConfigurations {

    @Bean //serve para indicar que esse método retorna um bean gerenciado pelo Spring
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Desabilita proteção CSRF pois usaremos JWT (stateless)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Configura sessão como STATELESS para JWT
                .authorizeHttpRequests(req -> {
                    req.requestMatchers(new AntPathRequestMatcher("/login", "POST")).permitAll(); // Permite POST /login
                    req.requestMatchers("/teste-senha").permitAll(); // Permite teste de senha
                    req.anyRequest().authenticated(); // Todas as outras rotas precisam de autenticação
                })
                .formLogin(form -> form.disable()) // Desabilita form login padrão
                .httpBasic(basic -> basic.disable()) // Desabilita HTTP Basic Auth
                .exceptionHandling(ex -> ex.authenticationEntryPoint((request, response, authException) -> {
                    response.setStatus(401); // Força retorno 401 em vez de 403
                }))
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