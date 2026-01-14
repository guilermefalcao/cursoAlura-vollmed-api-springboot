package med.voll.api.domain.consulta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;

@Service  //significa que essa classe é um serviço do Spring, que contem regras de negocio e validacoes
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;


    public void agendar(DadosAgendamentoConsulta dados) {
        //objetivo é salvar no banco de dados a consulta agendada      
        //aqui vao ter as regras de negocio e validacoes:

        //os dois ifs   sao para validar se o id do paciente e do medico existem no banco de dados
        if (!pacienteRepository.existsById(dados.idPaciente())) {
            throw new ValidacaoException("ID do paciente informado nao existe");
        }

        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
            throw new ValidacaoException("ID do medico informado nao existe");
        }


        //aqui para baixo, é para salvar a consulta no banco de dados
        
        // Busca o paciente no banco pelo ID informado
        var paciente = pacienteRepository.findById(dados.idPaciente()).get();
        
        // Busca o médico no banco pelo ID informado ou escolhe um aleatório
        var medico = escolherMedico(dados);
        
        // Cria uma nova consulta com os dados (id será gerado automaticamente pelo banco)
        var consulta = new Consulta(null, medico, paciente, dados.data());
        
        // Salva a consulta no banco de dados
        consultaRepository.save(consulta);
        
    }


    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        // Se o ID do médico foi informado, busca esse médico específico
        if (dados.idMedico() != null) {
            return medicoRepository.getReferenceById(dados.idMedico());
        }
        
        // Se a especialidade não foi informada, lança exceção
        if (dados.especialidade() == null) {
            throw new ValidacaoException("Especialidade eh obrigatoria quando o medico nao for escolhido");
        }
        
        // Escolhe um médico aleatório livre na data informada
        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
    }

}



//tem q injetar esta classe no controller de consultas para chamar as regras de negocio de agendamento de consultas
//classe responsavel por gerenciar o agendamento de consultas medicas,