package med.voll.api.domain.consulta.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;

@Component // Indica que é um componente gerenciado pelo Spring
public class ValidadorMedicoComOutraConsultaNoMesmoHorario implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private ConsultaRepository repository;

    public void validar(DadosAgendamentoConsulta dados) {
        // Verifica se o médico já tem consulta agendada no mesmo horário (desconsiderando canceladas)
        var medicoPossuiOutraConsultaNoMesmoHorario = repository.existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(
            dados.idMedico(), 
            dados.data()
        );
        
        if (medicoPossuiOutraConsultaNoMesmoHorario) {
            throw new ValidacaoException("Medico ja possui outra consulta agendada nesse mesmo horario");
        }
    }

}

// Validação para garantir que um médico não tenha mais de uma consulta no mesmo horário
// Como as consultas têm duração fixa de 1 hora, basta verificar se existe consulta na mesma data/hora
