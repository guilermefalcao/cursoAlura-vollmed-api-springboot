package med.voll.api.medico;

import med.voll.api.endereco.DadosEndereco;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosCadastroMedico(

        @NotBlank // verifica se nao eh nulo e nao vazio , notblank é para strings e notnull é
                  // para objetos
        String nome,

        @NotBlank @Email // verifica se o formato do email eh valido
        String email,

        @NotBlank
        String telefone,

        @NotBlank @Pattern(regexp = "\\d{4,6}") // verifica se o crm tem entre 4 e 6 digitos
        String crm,

        @NotNull // verifica se nao eh nulo
        Especialidade especialidade,

        @NotNull @Valid // serve para validar os dados do endereco que eh um objeto complexo
        DadosEndereco endereco) {

}

// explique o que é um record em java
// um record em java é uma forma concisa de definir uma classe imutável que
// serve como um contêiner para dados
// neste exemplo os dados do medico são: nome, email, telefone, crm e
// especialidade
// o java gera automaticamente os métodos construtor, getters, equals, hashCode
// e toString
// especialidade é do tipo enum porque dentro dele tem 4 tipos e os outros como
// nome, email so é um tipo

// dados endereço foi criado um novo record porque tem muitos campos
// e para manter o código mais organizado e legível

// usando bean validation para validar os dados de entrada
// poderia adicionar anotações de validação nos campos do record


//faça uma lista comentada abaixo com as princiais anotaçoes @ de validation:
// @NotBlank : verifica se o campo nao é nulo e nao é vazio (aplicável para strings)
// @NotNull : verifica se o campo nao é nulo    
// @Email : verifica se o campo tem o formato de email válido
// @Pattern(regexp = "regex") : verifica se o campo corresponde ao padrão regex fornecido
// @Size(min = x, max = y) : verifica se o tamanho do campo está entre x e y (aplicável para strings, coleções, arrays)
// @Min(x) : verifica se o valor numérico do campo é maior ou igual a x
// @Max(x) : verifica se o valor numérico do campo é menor ou igual a x
// @Positive : verifica se o valor numérico do campo é positivo
// @Negative : verifica se o valor numérico do campo é negativo
// @Future : verifica se a data do campo é no futuro
// @Past : verifica se a data do campo é no passado
// @FutureOrPresent : verifica se a data do campo é no futuro ou presente
// @PastOrPresent : verifica se a data do campo é no passado ou presente
// essas anotações ajudam a garantir que os dados recebidos pela API estejam no formato esperado antes de serem processados ou armazenados no banco de dados