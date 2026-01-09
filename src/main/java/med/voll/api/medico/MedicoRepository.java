package med.voll.api.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.util.Streamable;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

    Page<Medico> findAllByAtivoTrue(Pageable paginacao);  //serve para listar apenas os medicos ativos, usado na paginaçao

}