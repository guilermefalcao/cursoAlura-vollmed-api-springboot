package med.voll.api.domain.consulta.validacoes;

import org.springframework.stereotype.Component;

import med.voll.api.domain.consulta.DadosAgendamentoConsulta;

@Component // Indica que é um componente gerenciado pelo Spring 
public class ValidadorHorarioFuncionamentoClinica implements ValidadorAgendamentoDeConsulta {

    public void validar(DadosAgendamentoConsulta dados  ) {
        //implementar a validaçao do horario de funcionamento da clinica
        //se a data da consulta for um domingo, lança uma exceção
        var domingo = dados.data().getDayOfWeek().name().equals("SUNDAY");
        if (domingo) {
            throw new RuntimeException("Consulta não pode ser agendada para domingo");
        }   
        //se a hora da consulta for antes das 7h ou depois das 18h, lança uma exceção
        var hora = dados.data().getHour();
        if (hora < 7 || hora > 18) {
            throw new RuntimeException("Consulta deve ser agendada no horário de funcionamento da clínica (7h às 19h)");
        }
    }   

}



// implements ValidadorAgendamentoDeConsulta   
//validação para garantir que a consulta seja agendada dentro do horário de funcionamento da clínica