package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teste-senha")
public class TesteSenhaController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<String> testarSenha() {
        String senhaTexto = "123456";
        String hashBanco = "$2a$10$Y50UaMFOxteibQEYLrwuHeehHYfcoafCopUazP12.rqB91YBYAMIm";
        
        // Gera novo hash
        String novoHash = passwordEncoder.encode(senhaTexto);
        
        // Testa se a senha bate com o hash do banco
        boolean senhaCorreta = passwordEncoder.matches(senhaTexto, hashBanco);
        
        String resultado = "Senha: " + senhaTexto + "\n" +
                          "Hash banco: " + hashBanco + "\n" +
                          "Novo hash: " + novoHash + "\n" +
                          "Senha correta: " + senhaCorreta;
        
        return ResponseEntity.ok(resultado);
    }
}