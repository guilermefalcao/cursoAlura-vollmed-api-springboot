package med.voll.api.infra.security;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuario.UsuarioRepository;

@Component //indica que essa classe é um componente gerenciado pelo Spring, que pode ser injetado em outras classes
public class SecurityFilter extends OncePerRequestFilter { //OncePerRequestFilter garante que o filtro seja executado apenas uma vez por requisição


    @Autowired  //vai importar o TokenService para validar o token JWT
    private TokenService tokenService; //o import é:   med.voll.api.infra.security.TokenService
  
    @Autowired
    private UsuarioRepository repository;
  



    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws IOException, ServletException {
        
        var tokenJWT = recuperarToken(request);

        if (tokenJWT != null) {
            try {
                var subject = tokenService.getSubject(tokenJWT);
                var usuario = repository.findByLogin(subject);
                
                var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (RuntimeException e) {
                // Token inválido ou expirado - não autentica o usuário
                // Spring Security vai bloquear automaticamente
            }
        }
        
        filterChain.doFilter(request, response);
    }
    
    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }

}

// EXPLICAÇÃO DO FILTRO:
// ========================
// Este filtro será executado uma única vez por requisição HTTP
// Aqui implementaremos a lógica para validar tokens JWT
// Se o token for válido, permite acesso; se inválido, bloqueia