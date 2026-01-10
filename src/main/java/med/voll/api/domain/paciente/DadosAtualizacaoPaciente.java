package med.voll.api.domain.paciente;

import jakarta.validation.Valid;
import med.voll.api.domain.endereco.DadosEndereco;

//record para receber dados de atualização do paciente
//contém apenas os campos que podem ser alterados
//id é obrigatório para identificar qual paciente atualizar
public record DadosAtualizacaoPaciente(
    Long id, //obrigatório para identificar o paciente
    String nome, //opcional - só atualiza se não for null
    String telefone, //opcional - só atualiza se não for null
    @Valid DadosEndereco endereco //opcional - só atualiza se não for null, @Valid valida os dados do endereço
) {
}
