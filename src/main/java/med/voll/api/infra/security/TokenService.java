package med.voll.api.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import med.voll.api.domain.usuario.Usuario;

@Service // Spring vai carregar essa classe como um bean de serviço
public class TokenService { // Classe de serviço relacionada a tokens JWT - geração e validação

    // Chave secreta para assinar o token JWT (valor fixo para desenvolvimento)
    // Em produção, deve vir de variável de ambiente ou application.properties
    private String secret = "12345678";

    // Método responsável por gerar o token JWT após autenticação bem-sucedida
    public String gerarToken(Usuario usuario) {
        //System.out.println("DEBUG: Gerando token para o usuário: " + secret);
        try {
            // Define o algoritmo de criptografia HMAC256 com a chave secreta
            var algoritmo = Algorithm.HMAC256(secret);
            
            // Cria e configura o token JWT
            return JWT.create()
                .withIssuer("API Voll.med") // Identifica quem emitiu o token
                .withSubject(usuario.getLogin()) // Identifica o usuário (subject do token)
                .withExpiresAt(dataExpiracao()) // Define quando o token expira
                // .withClaim("id", usuario.getId()) // Informações adicionais (opcional)
                .sign(algoritmo); // Assina o token com o algoritmo definido
                
        } catch (JWTCreationException exception) {
            // Lança exceção em caso de erro na geração do token
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }

    // Método privado que calcula a data de expiração do token (2 horas a partir de agora)
    private Instant dataExpiracao() {
        return LocalDateTime.now()
            .plusHours(2) // Adiciona 2 horas ao momento atual
            .toInstant(ZoneOffset.of("-03:00")); // Converte para Instant com fuso horário do Brasil
    }
}
