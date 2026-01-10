package med.voll.api.domain.paciente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.DadosEndereco;

//record para receber os dados de cadastro do paciente via JSON
//usando Bean Validation para validar os dados de entrada
public record DadosCadastroPaciente(
        // Campos obrigatórios do paciente com mensagens personalizadas
        @NotBlank(message = "{nome.obrigatorio}")
        String nome,
        
        // Email: campo obrigatório E formato válido
        @NotBlank(message = "{email.obrigatorio}")
        @Email(message = "{email.invalido}")
        String email,
        
        @NotBlank(message = "{telefone.obrigatorio}")
        String telefone,
        
        // CPF: campo obrigatório E formato válido (XXX.XXX.XXX-XX)
        @NotBlank(message = "{cpf.obrigatorio}")
        @Pattern(regexp = "\\d{3}\\.?\\d{3}\\.?\\d{3}\\-?\\d{2}", message = "{cpf.invalido}")
        String cpf,
        
        // Endereço: objeto obrigatório com validação recursiva
        @NotNull(message = "{endereco.obrigatorio}")
        @Valid
        DadosEndereco endereco
) {
}
