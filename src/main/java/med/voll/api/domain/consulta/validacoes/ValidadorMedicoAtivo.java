package med.voll.api.domain.consulta.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.medico.MedicoRepository;

@Component // Indica que é um componente gerenciado pelo Spring
public class ValidadorMedicoAtivo implements ValidadorAgendamentoDeConsulta {

    @Autowired
    private MedicoRepository repository;

    public void validar(DadosAgendamentoConsulta dados) {
        // Se o médico foi escolhido, verifica se está ativo
        if (dados.idMedico() == null) {
            return; // Se não foi informado, não precisa validar (será escolhido aleatoriamente)
        }

        var medicoEstaAtivo = repository.findAtivoById(dados.idMedico());
        
        if (!medicoEstaAtivo) {
            throw new ValidacaoException("Consulta nao pode ser agendada com medico inativo");
        }
    }

}

// Validação para garantir que apenas médicos ativos possam ter consultas agendadas
