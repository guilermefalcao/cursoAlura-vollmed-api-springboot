package med.voll.api.domain.consulta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.validacoes.ValidadorAgendamentoDeConsulta;
import med.voll.api.domain.consulta.validacoes.cancelamento.ValidadorCancelamentoDeConsulta;
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


    @Autowired
    private List<ValidadorAgendamentoDeConsulta> validadores;  //spring injeta todas as implementações dessa interface que estão anotadas com @Component

    @Autowired
    private List<ValidadorCancelamentoDeConsulta> validadoresCancelamento;  //spring injeta todos os validadores de cancelamento


    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) {
        //objetivo é salvar no banco de dados a consulta agendada      
        //aqui vao ter as regras de negocio e validacoes:

        //os dois ifs   sao para validar se o id do paciente e do medico existem no banco de dados
        if (!pacienteRepository.existsById(dados.idPaciente())) {
            throw new ValidacaoException("ID do paciente informado nao existe");
        }

        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
            throw new ValidacaoException("ID do medico informado nao existe");
        }


        validadores.forEach(v -> v.validar(dados)); //chama todos os validadores registrados para validar os dados da consulta  
        //aqui para baixo, é para salvar a consulta no banco de dados,
        //aplicando aqui os 3 principios do solid: single responsibility, open closed e dependency inversion
        // single responsibility: essa classe so gerencia o agendamento de consultas
        // open closed: se precisar adicionar mais validações, só criar uma nova classe que implementa a interface ValidadorAgendamentoDeConsulta e anotar com @Component, sem precisar alterar essa classe
        // dependency inversion: essa classe depende da abstração (interface ValidadorAgendamentoDeConsulta) e não das implementações concretas (classes que implementam essa interface)
        
        
        // Busca o paciente no banco pelo ID informado
        var paciente = pacienteRepository.findById(dados.idPaciente()).get();
        
        // Busca o médico no banco pelo ID informado ou escolhe um aleatório
        var medico = escolherMedico(dados);
        if (medico == null) {
            throw new ValidacaoException("Nao ha medicos disponiveis nessa data");
            
        }
        
        // Cria uma nova consulta com os dados (id e motivoCancelamento serão gerados/preenchidos depois)
        var consulta = new Consulta(null, medico, paciente, dados.data(), null);
        
        // Salva a consulta no banco de dados
        consultaRepository.save(consulta);

        return new DadosDetalhamentoConsulta(consulta);
        
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

    // Método para cancelar uma consulta agendada
    public void cancelar(DadosCancelamentoConsulta dados) {
        // Valida se o ID da consulta existe no banco
        if (!consultaRepository.existsById(dados.idConsulta())) {
            throw new ValidacaoException("Id da consulta informado nao existe!");
        }

        // Chama todos os validadores de cancelamento registrados
        validadoresCancelamento.forEach(v -> v.validar(dados));

        // Busca a consulta no banco pelo ID
        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        
        // Cancela a consulta registrando o motivo
        consulta.cancelar(dados.motivo());
    }

}



//tem q injetar esta classe no controller de consultas para chamar as regras de negocio de agendamento de consultas
//classe responsavel por gerenciar o agendamento de consultas medicas,