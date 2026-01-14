package med.voll.api.domain.consulta;

import java.time.LocalDateTime;

public record DadosDetalhamentoConsulta(
    Long id,
    Long idMedico,
    Long idPaciente,
    LocalDateTime data
) {

    public DadosDetalhamentoConsulta(Consulta consulta) {   //dto que recebe uma consulta e extrai os dados para preencher o record
       this(consulta.getId(), consulta.getMedico().getId(), consulta.getPaciente().getId(), consulta.getData());
    }

}


//dto q vai devolver os dados da consulta agendada pela api