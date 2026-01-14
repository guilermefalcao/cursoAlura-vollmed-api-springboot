package med.voll.api.domain.consulta.validacoes.cancelamento;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosCancelamentoConsulta;

@Component("ValidadorHorarioAntecedenciaCancelamento") // Nome único para evitar conflito com validador de agendamento
public class ValidadorHorarioAntecedencia implements ValidadorCancelamentoDeConsulta {

    @Autowired
    private ConsultaRepository repository;

    @Override
    public void validar(DadosCancelamentoConsulta dados) {
        // Busca a consulta no banco pelo ID
        var consulta = repository.getReferenceById(dados.idConsulta());
        
        // Pega a data/hora atual
        var agora = LocalDateTime.now();
        
        // Calcula a diferença em horas entre agora e a data da consulta
        var diferencaEmHoras = Duration.between(agora, consulta.getData()).toHours();

        // Valida se a consulta está sendo cancelada com pelo menos 24h de antecedência
        if (diferencaEmHoras < 24) {
            throw new ValidacaoException("Consulta somente pode ser cancelada com antecedencia minima de 24h!");
        }
    }
}

// Validação para garantir que consultas só sejam canceladas com antecedência mínima de 24 horas
