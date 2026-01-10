package med.voll.api.domain.medico;

import med.voll.api.domain.endereco.DadosEndereco;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosCadastroMedico(

        // @NotBlank com mensagem personalizada - verifica se não é nulo e não é vazio
        // A mensagem é buscada no arquivo ValidationMessages.properties usando a chave entre {}
        @NotBlank(message = "{nome.obrigatorio}")
        String nome,

        // Múltiplas validações: campo obrigatório E formato de email válido
        // Cada anotação pode ter sua própria mensagem personalizada
        @NotBlank(message = "{email.obrigatorio}")
        @Email(message = "{email.invalido}")
        String email,

        // Validação simples de campo obrigatório
        @NotBlank(message = "{telefone.obrigatorio}")
        String telefone,

        // Validação combinada: campo obrigatório E padrão regex (4 a 6 dígitos)
        @NotBlank(message = "{crm.obrigatorio}")
        @Pattern(regexp = "\\d{4,6}", message = "{crm.invalido}")
        String crm,

        // @NotNull para objetos (enum) - verifica se não é nulo
        @NotNull(message = "{especialidade.obrigatoria}")
        Especialidade especialidade,

        // @Valid para validar objeto complexo (DadosEndereco) recursivamente
        // @NotNull garante que o objeto endereco não seja nulo
        @NotNull(message = "{endereco.obrigatorio}")
        @Valid
        DadosEndereco endereco) {

}

// PERSONALIZAÇÃO DE MENSAGENS DE VALIDAÇÃO:
// ==========================================
// 1. Criamos o arquivo ValidationMessages.properties em src/main/resources
// 2. Definimos chaves e mensagens personalizadas (ex: nome.obrigatorio=Nome é obrigatório)
// 3. Nas anotações de validação, usamos message = "{chave}" para referenciar as mensagens
// 4. O Spring Boot automaticamente busca as mensagens neste arquivo
// 5. Vantagens: mensagens centralizadas, fácil manutenção, suporte à internacionalização
//
// COMO FUNCIONA:
// - Quando uma validação falha, o Spring busca a mensagem pela chave no arquivo .properties
// - Se não encontrar a chave, usa a mensagem padrão da anotação
// - As mensagens aparecem no JSON de erro retornado pela API (tratado em TratadorDeErros)
//
// EXEMPLO DE USO:
// @NotBlank(message = "{nome.obrigatorio}") -> busca "nome.obrigatorio" no arquivo
// Se a validação falhar, retorna "Nome é obrigatório" em vez da mensagem padrão em inglês
