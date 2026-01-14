package med.voll.api.domain.consulta;

import java.time.LocalDateTime;

public record DadosDetalhamentoConsulta(
    Long id,
    Long idMedico,
    Long idPaciente,
    LocalDateTime data
) {

}


//dto q vai devolver os dados da consulta agendada pela api