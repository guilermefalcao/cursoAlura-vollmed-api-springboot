package med.voll.api.domain.consulta;

import jakarta.validation.constraints.NotNull;

// DTO (Data Transfer Object) para receber os dados de cancelamento de consulta da API
public record DadosCancelamentoConsulta(
        
        @NotNull // Validação: ID da consulta é obrigatório
        Long idConsulta,

        @NotNull // Validação: Motivo do cancelamento é obrigatório
        MotivoCancelamento motivo) {
}
