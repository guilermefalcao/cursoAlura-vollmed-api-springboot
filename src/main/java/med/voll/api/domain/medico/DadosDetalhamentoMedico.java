package med.voll.api.domain.medico;

import med.voll.api.domain.endereco.Endereco;

//record para retornar detalhes completos do médico
//usado principalmente no retorno de operações PUT (atualização)
//mostra todos os dados atualizados do médico
public record DadosDetalhamentoMedico(
    Long id,
    String nome,
    String email,
    String crm,
    String telefone,
    Especialidade especialidade,
    Endereco endereco
) {
    
    //construtor que recebe um objeto Medico e extrai todos os dados
    //usado para converter a entidade em DTO de resposta
    public DadosDetalhamentoMedico(Medico medico) {
        this(
            medico.getId(),
            medico.getNome(),
            medico.getEmail(),
            medico.getCrm(),
            medico.getTelefone(),
            medico.getEspecialidade(),
            medico.getEndereco()
        );
    }
}
