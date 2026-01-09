package med.voll.api.paciente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.endereco.DadosEndereco;

//record para receber os dados de cadastro do paciente via JSON
//usando Bean Validation para validar os dados de entrada
public record DadosCadastroPaciente(
        @NotBlank //verifica se não é nulo e não vazio, notblank é para strings
        String nome,
        
        @NotBlank 
        @Email //verifica se o formato do email é válido
        String email,
        
        @NotBlank 
        String telefone,
        
        @NotBlank 
        @Pattern(regexp = "\\d{3}\\.?\\d{3}\\.?\\d{3}\\-?\\d{2}") //verifica se o CPF tem o formato correto (XXX.XXX.XXX-XX)
        String cpf,
        
        @NotNull //verifica se não é nulo
        @Valid //serve para validar os dados do endereco que é um objeto complexo
        DadosEndereco endereco
) {
}
