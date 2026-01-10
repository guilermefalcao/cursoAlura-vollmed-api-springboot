package med.voll.api.domain.medico;

//record para listar apenas o nome dos médicos
//útil quando precisamos de uma listagem mais simples, apenas com nomes
public record DadosListagemMedicoNome(String nome) {
    
    //construtor que recebe um objeto Medico e extrai apenas o nome
    public DadosListagemMedicoNome(Medico medico) {
        this(medico.getNome());
    }
}
