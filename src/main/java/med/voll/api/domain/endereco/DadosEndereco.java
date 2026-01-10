package med.voll.api.domain.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosEndereco(
    
    // Campos obrigatórios do endereço com mensagens personalizadas
    @NotBlank(message = "{logradouro.obrigatorio}")
    String logradouro, 
    
    @NotBlank(message = "{bairro.obrigatorio}")
    String bairro, 
    
    // CEP: campo obrigatório E deve ter exatamente 8 dígitos
    @NotBlank(message = "{cep.obrigatorio}")
    @Pattern(regexp = "\\d{8}", message = "{cep.invalido}")
    String cep, 
    
    @NotBlank(message = "{cidade.obrigatoria}")
    String cidade, 
    
    @NotBlank(message = "{uf.obrigatorio}")
    String uf, 
    
    // Campos opcionais - não precisam de validação
    String complemento, 
    String numero) {

}

//foi criado esse record DadosEndereco para organizar melhor os dados do endereço
//porque tem muitos campos e ficaria bagunçado colocar tudo no record DadosCadastroMedico
