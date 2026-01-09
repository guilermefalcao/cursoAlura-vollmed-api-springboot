package med.voll.api.paciente;

import med.voll.api.endereco.Endereco;

//record para retornar detalhes completos do paciente
//usado principalmente no retorno de operações GET (detalhamento) e PUT (atualização)
//mostra todos os dados do paciente
public record DadosDetalhamentoPaciente(
    Long id,
    String nome,
    String email,
    String cpf,
    String telefone,
    Endereco endereco
) {
    
    //construtor que recebe um objeto Paciente e extrai todos os dados
    //usado para converter a entidade em DTO de resposta
    public DadosDetalhamentoPaciente(Paciente paciente) {
        this(
            paciente.getId(),
            paciente.getNome(),
            paciente.getEmail(),
            paciente.getCpf(),
            paciente.getTelefone(),
            paciente.getEndereco()
        );
    }
}