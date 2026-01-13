package med.voll.api.infra.security;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component //indica que essa classe é um componente gerenciado pelo Spring, que pode ser injetado em outras classes
public class SecurityFilter extends OncePerRequestFilter { //OncePerRequestFilter garante que o filtro seja executado apenas uma vez por requisição

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws IOException, ServletException {
        
        System.out.println("DEBUG: filtro chamado para URL: " + request.getRequestURI());
        
        // para recuperar o cabeçalho   
        var tokenJWT = recuperarToken(request);

        if (tokenJWT == null) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Token JWT nao enviado no cabecalho");
            return;
        }

        System.out.println(tokenJWT);
        
        // Continua a cadeia de filtros
        filterChain.doFilter(request, response);  //chama o proximo filtro na cadeia
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