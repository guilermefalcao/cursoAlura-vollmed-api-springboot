package med.voll.api.domain.paciente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

//interface que extende JpaRepository para operações de banco de dados
//JpaRepository já possui métodos prontos como save, findAll, findById, delete, etc
//não precisa implementar nada, o Spring Data JPA faz isso automaticamente
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    //método customizado para buscar apenas pacientes ativos com paginação
    //Spring Data JPA cria automaticamente a query baseada no nome do método
    //findAllByAtivoTrue = SELECT * FROM pacientes WHERE ativo = true
    Page<Paciente> findAllByAtivoTrue(Pageable paginacao);

    @Query("SELECT p.ativo FROM Paciente p WHERE p.id = :id")
    Boolean findAtivoById(Long id);

}
