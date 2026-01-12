package med.voll.api.domain.usuario;

import jakarta.validation.constraints.NotBlank;

// Record para receber dados de login via JSON
// Usado no endpoint POST /login para autenticação
public record DadosAutenticacao(
        
        @NotBlank(message = "{login.obrigatorio}") // Validação: login não pode ser vazio
        String login,
        
        @NotBlank(message = "{senha.obrigatoria}") // Validação: senha não pode ser vazia
        String senha
        
) {
    
}

// EXEMPLO DE USO:
// POST /login
// {
//   "login": "admin",
//   "senha": "123456"
// }