package med.voll.api.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosEndereco(
    
    @NotBlank //verifica se nao eh nulo e nao vazio , notblank é para strings e notnull é para objetos
    String logradouro, 
    
    @NotBlank 
    String bairro, 
    
    @NotBlank 
    @Pattern(regexp = "\\d{8}") //verifica se o cep tem 8 digitos
    String cep, 
    
    @NotBlank 
    String cidade, 
    
    @NotBlank 
    String uf, 
    
    
    String complemento, 
    
    
    String numero) {

}

//foi criado esse record DadosEndereco para organizar melhor os dados do endereço
//porque tem muitos campos e ficaria bagunçado colocar tudo no record DadosCadastroMedico
