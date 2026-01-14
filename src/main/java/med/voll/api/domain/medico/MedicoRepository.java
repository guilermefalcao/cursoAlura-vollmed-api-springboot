package med.voll.api.domain.medico;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.util.Streamable;
import org.springframework.data.jpa.repository.Query;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

    Page<Medico> findAllByAtivoTrue(Pageable paginacao);  //serve para listar apenas os medicos ativos, usado na paginaçao


    @Query("""
            SELECT m FROM Medico m 
            WHERE m.ativo = true 
            AND m.especialidade = :especialidade
            AND m.id not in(
            
                SELECT c.medico.id FROM Consulta c 
                WHERE c.data = :data
            )
            ORDER BY RAND() LIMIT 1
            """) //consulta personalizada para escolher um medico aleatorio livre na data especificada  
                //vai trazer um medico aleatorio que esteja ativo e que tenha a especialidade informada e que esteja disponivel na data informada
    Medico escolherMedicoAleatorioLivreNaData(Especialidade especialidade,
            LocalDateTime data);

}
//interface que extende JpaRepository para fornecer operações de CRUD para a entidade Medico

//método personalizado para escolher um médico aleatório disponível em uma data específica
//com base na especialidade e data fornecidas.