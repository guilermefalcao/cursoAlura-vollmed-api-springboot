package med.voll.api.domain.consulta.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;

@Component // Indica que é um componente gerenciado pelo Spring
public class ValidadorPacienteComOutraConsultaNoDia implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private ConsultaRepository repository;

    public void validar(DadosAgendamentoConsulta dados) {
        // Pega o primeiro e último horário do dia da consulta
        var primeiroHorario = dados.data().withHour(7);
        var ultimoHorario = dados.data().withHour(18);
        
        // Verifica se o paciente já tem consulta agendada nesse dia
        var pacientePossuiOutraConsultaNoDia = repository.existsByPacienteIdAndDataBetween(
            dados.idPaciente(), 
            primeiroHorario, 
            ultimoHorario
        );
        
        if (pacientePossuiOutraConsultaNoDia) {
            throw new ValidacaoException("Paciente ja possui uma consulta agendada nesse dia");
        }
    }

}

// Validação para garantir que um paciente não tenha mais de uma consulta no mesmo dia
// Considera o horário de funcionamento da clínica (07:00 às 19:00)
