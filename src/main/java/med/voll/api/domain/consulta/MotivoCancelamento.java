package med.voll.api.domain.consulta;

// Enum que representa os motivos possíveis para cancelamento de uma consulta
public enum MotivoCancelamento {

    PACIENTE_DESISTIU,  // Quando o paciente desiste da consulta
    MEDICO_CANCELOU,    // Quando o médico cancela a consulta
    OUTROS;             // Outros motivos não especificados

}
