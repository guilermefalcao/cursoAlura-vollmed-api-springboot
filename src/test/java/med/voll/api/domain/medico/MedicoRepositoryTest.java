package med.voll.api.domain.medico;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.paciente.DadosCadastroPaciente;
import med.voll.api.domain.paciente.Paciente;

// Anotação que indica que esta classe contém testes de repositório JPA
// Configura um contexto Spring reduzido, carregando apenas componentes JPA
// Ideal para testar repositories, entities e queries


// Configura o banco de dados para os testes
// Replace.NONE = NÃO substitui o banco configurado (usa o MySQL real)
// Por padrão, @DataJpaTest usa banco em memória (H2), mas aqui forçamos usar o MySQL
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;   

    @Autowired
    private TestEntityManager em; // Gerencia entidades no contexto de teste (persist, flush, etc)
    
    // Anotação que indica que este é um método de teste
    // JUnit vai executar este método ao rodar os testes
    @Test
    @DisplayName("deveria devolver null quando unico medico cadastrado nao esta disponivel na data")
    void testEscolherMedicoAleatorioLivreNaDataCenario1() {
        // Given (Cenário): Cadastra 1 médico e 1 consulta no mesmo horário que será buscado
        var proximaSegundaAs10 = LocalDateTime.now()
            .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
            .withHour(10).withMinute(0).withSecond(0);

        var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);
        var paciente = cadastrarPaciente("Paciente", "paciente@email.com", "00000000000");
        cadastrarConsulta(medico, paciente, proximaSegundaAs10);

        // When (Ação): Busca médico livre na mesma data/hora que já tem consulta
        var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);
        
        // Then (Verificação): Deve retornar null pois o médico está ocupado
        assertThat(medicoLivre).isNull();
    }

    @Test
    @DisplayName("deveria devolver medico quando ele estiver disponivel na data")
    void testEscolherMedicoAleatorioLivreNaDataCenario2() {
        // Given (Cenário): Cadastra 1 médico sem consultas no horário buscado
        var proximaSegundaAs10 = LocalDateTime.now()
            .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
            .withHour(10).withMinute(0).withSecond(0);

        var medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);

        // When (Ação): Busca médico livre na data
        var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);
        
        // Then (Verificação): Deve retornar o médico pois ele está disponível
        assertThat(medicoLivre).isEqualTo(medico);
    }




    

    // Método auxiliar: Cadastra uma consulta no banco de teste
    private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
        em.persist(new Consulta(null, medico, paciente, data, null));
    }

    // Método auxiliar: Cadastra um médico no banco de teste
    private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade) {
        var medico = new Medico(dadosMedico(nome, email, crm, especialidade));
        em.persist(medico);
        return medico;
    }

    // Método auxiliar: Cadastra um paciente no banco de teste
    private Paciente cadastrarPaciente(String nome, String email, String cpf) {
        var paciente = new Paciente(dadosPaciente(nome, email, cpf));
        em.persist(paciente);
        return paciente;
    }

    // Método auxiliar: Cria dados de cadastro do médico
    private DadosCadastroMedico dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
        return new DadosCadastroMedico(
            nome,
            email,
            "61999999999",
            crm,
            especialidade,
            dadosEndereco()
        );
    }

    // Método auxiliar: Cria dados de cadastro do paciente
    private DadosCadastroPaciente dadosPaciente(String nome, String email, String cpf) {
        return new DadosCadastroPaciente(
            nome,
            email,
            "61999999999",
            cpf,
            dadosEndereco()
        );
    }

    // Método auxiliar: Cria dados de endereço padrão
    private DadosEndereco dadosEndereco() {
        return new DadosEndereco(
            "rua xpto",
            "bairro",
            "00000000",
            "Brasilia",
            "DF",
            null,
            null
        );
    }
        
}
