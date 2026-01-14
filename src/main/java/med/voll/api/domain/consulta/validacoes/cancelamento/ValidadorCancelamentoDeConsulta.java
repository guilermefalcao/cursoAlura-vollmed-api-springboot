package med.voll.api.domain.consulta.validacoes.cancelamento;

import med.voll.api.domain.consulta.DadosCancelamentoConsulta;

// Interface que define o contrato para validadores de cancelamento de consulta
public interface ValidadorCancelamentoDeConsulta {

    void validar(DadosCancelamentoConsulta dados);

}
