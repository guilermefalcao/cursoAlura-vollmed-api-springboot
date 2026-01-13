package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import med.voll.api.domain.usuario.Usuario;
import med.voll.api.infra.security.DadosTokenJWT;
import med.voll.api.infra.security.TokenService;

@RestController  //serve para indicar que essa classe é um controlador REST
@RequestMapping("/login")  //serve para mapear a rota /login para essa classe
public class AutenticacaoController {

    // AuthenticationManager do Spring Security que serve para autenticar o usuário
    private final AuthenticationManager manager;
    
    // TokenService para gerar tokens JWT após autenticação bem-sucedida
    private final TokenService tokenService;

    // Injeção via construtor (mais recomendada que @Autowired)
    // Spring injeta automaticamente as dependências
    public AutenticacaoController(AuthenticationManager manager, TokenService tokenService) {
        this.manager = manager;
        this.tokenService = tokenService;
    }

    @PostMapping //mapeia requisições POST para /login
    public ResponseEntity<DadosTokenJWT> efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
        
        // Cria um token de autenticação com login e senha recebidos no DTO
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        
        // Autentica o usuário usando o AuthenticationManager
        // Se as credenciais estiverem incorretas, lança uma exceção
        var authentication = manager.authenticate(authenticationToken);
    
        // Obtém o usuário autenticado do objeto Authentication
        // getPrincipal() retorna o UserDetails (nossa classe Usuario)
        var usuario = (Usuario) authentication.getPrincipal();
        
        // Gera o token JWT usando o TokenService
        var tokenJWT = tokenService.gerarToken(usuario);
        
        // Retorna o token JWT no corpo da resposta com status 200 OK
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }

}