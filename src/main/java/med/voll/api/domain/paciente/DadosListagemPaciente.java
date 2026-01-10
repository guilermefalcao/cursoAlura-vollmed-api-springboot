package med.voll.api.domain.paciente;

//record para listagem de pacientes
//contém apenas os campos essenciais que queremos exibir na listagem
//agora inclui o ID para permitir operações de atualização e exclusão
public record DadosListagemPaciente(Long id, String nome, String email, String cpf) {
    
    //construtor que recebe um objeto Paciente e extrai os campos necessários
    //this() chama o construtor principal do record com os valores extraídos
    //agora inclui o ID do paciente para operações CRUD
    public DadosListagemPaciente(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getCpf());
    }
}
