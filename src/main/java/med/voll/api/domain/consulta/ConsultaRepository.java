package med.voll.api.domain.consulta;

import org.springframework.data.jpa.repository.JpaRepository;

public interface  ConsultaRepository extends JpaRepository<Consulta, Long> {

}
//interface que extende JpaRepository para fornecer operações de CRUD para a entidade Consulta