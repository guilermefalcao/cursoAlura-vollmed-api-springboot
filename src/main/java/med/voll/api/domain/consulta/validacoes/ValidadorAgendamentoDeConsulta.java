package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.consulta.DadosAgendamentoConsulta;

public interface ValidadorAgendamentoDeConsulta {

    void validar(DadosAgendamentoConsulta dados);


}



//interface para validação do agendamento de consulta, que pode ter várias implementações diferentes
//interface nao precisa de @ pois o spring carrega automaticamente as classes que implementam essa interface que estão anotadas com @Component