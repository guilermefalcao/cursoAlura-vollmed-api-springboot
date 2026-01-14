package med.voll.api.domain.consulta;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

public interface  ConsultaRepository extends JpaRepository<Consulta, Long> {

    Boolean existsByPacienteIdAndDataBetween(Long idPaciente, LocalDateTime primeiroHorario, LocalDateTime ultimoHorario);

    Boolean existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(Long idMedico, LocalDateTime data);

}
//interface que extende JpaRepository para fornecer operações de CRUD para a entidade Consulta