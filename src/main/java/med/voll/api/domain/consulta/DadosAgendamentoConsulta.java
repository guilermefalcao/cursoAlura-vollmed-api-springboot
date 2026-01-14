package med.voll.api.domain.consulta;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.medico.Especialidade;

public record DadosAgendamentoConsulta(
    Long idMedico, 

    @NotNull
    Long idPaciente, 


    @NotNull
    @Future  //serve para garantir que a data seja no futuro , nao pode agendar uma consulta com data passada
    LocalDateTime data,
    

    Especialidade especialidade)
    
    {

}
    

//dto com os dados que vao chegar da api com dados do medico, paciente e data da consulta
