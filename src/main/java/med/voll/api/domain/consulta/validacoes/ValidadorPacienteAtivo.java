package med.voll.api.domain.consulta.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.paciente.PacienteRepository;

@Component // Indica que é um componente gerenciado pelo Spring
public class ValidadorPacienteAtivo implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private PacienteRepository repository;

    public void validar(DadosAgendamentoConsulta dados) {
        // Verifica se o paciente está ativo no sistema
        var pacienteEstaAtivo = repository.findAtivoById(dados.idPaciente());
        
        if (!pacienteEstaAtivo) {
            throw new ValidacaoException("Consulta nao pode ser agendada com paciente inativo");
        }
    }

}

// Validação para garantir que apenas pacientes ativos possam agendar consultas
