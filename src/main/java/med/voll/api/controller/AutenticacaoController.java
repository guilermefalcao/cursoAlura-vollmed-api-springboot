package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import med.voll.api.domain.usuario.DadosAutenticacao;

@RestController  //serve para indicar que essa classe é um controlador REST
@RequestMapping("/login")  //serve para mapear a rota /login para essa classe
public class AutenticacaoController {

    // AuthenticationManager do Spring Security que serve para autenticar o usuário
    private final AuthenticationManager manager;

    // Injeção via construtor (mais recomendada que @Autowired)
    public AutenticacaoController(AuthenticationManager manager) {
        this.manager = manager;
    }

    @PostMapping //mapeia requisições POST para /login
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
        
        // Cria um token de autenticação com login e senha recebidos no DTO
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        
        // Autentica o usuário usando o AuthenticationManager
        // Se as credenciais estiverem incorretas, lança uma exceção
        var authentication = manager.authenticate(authenticationToken);
    
        // Se chegou até aqui, a autenticação foi bem-sucedida
        // Retorna status 200 OK (futuramente retornará o token JWT)
        return ResponseEntity.ok().build();
    }

}

// EXPLICAÇÃO DO FLUXO DE AUTENTICAÇÃO:
// ====================================
// 1. Cliente envia POST /login com {"login": "user", "senha": "pass"}
// 2. @RequestBody converte JSON para DadosAutenticacao
// 3. @Valid valida os dados usando Bean Validation
// 4. Cria UsernamePasswordAuthenticationToken com as credenciais
// 5. AuthenticationManager autentica usando AutenticacaoService
// 6. Se válido, retorna 200 OK; se inválido, lança exceção (401 Unauthorized)
// 7. Futuramente, retornará um token JWT no corpo da resposta