package med.voll.api.domain.consulta.validacoes;


import org.springframework.stereotype.Component;

import med.voll.api.domain.consulta.DadosAgendamentoConsulta;

@Component // Indica que é um componente gerenciado pelo Spring 
public class ValidadorHorarioAntecedencia implements ValidadorAgendamentoDeConsulta {

       public void validar(DadosAgendamentoConsulta dados  ) {
        //agendamento tem que ser no minimo 30 minutos antes da consulta
        var agora =  java.time.LocalDateTime.now();
        var diferencaMinutos = java.time.Duration.between(agora, dados.data()).toMinutes();
        if (diferencaMinutos < 30) {
            throw new RuntimeException("Consulta deve ser agendada com antecedência mínima de 30 minutos");
        }   
    }  

}


//nao precisou ir no banco de dados, pq é uma validaçao simples, apenas compara a data atual com a data da consulta