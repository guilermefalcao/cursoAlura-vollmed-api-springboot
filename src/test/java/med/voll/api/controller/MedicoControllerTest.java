package med.voll.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.endereco.Endereco;
import med.voll.api.domain.medico.DadosCadastroMedico;
import med.voll.api.domain.medico.DadosDetalhamentoMedico;
import med.voll.api.domain.medico.Especialidade;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;

// Carrega o contexto completo do Spring para testar o controller
@SpringBootTest
// Configura o MockMvc para simular requisições HTTP
@AutoConfigureMockMvc
// Configura os conversores JSON para serializar/deserializar objetos nos testes
@AutoConfigureJsonTesters
class MedicoControllerTest {

    // Injeta o MockMvc que simula requisições HTTP sem subir o servidor
    @Autowired
    private MockMvc mvc;

    // Injeta o JacksonTester para converter DadosCadastroMedico em JSON
    @Autowired
    private JacksonTester<DadosCadastroMedico> dadosCadastroMedicoJson;

    // Injeta o JacksonTester para converter DadosDetalhamentoMedico em JSON
    @Autowired
    private JacksonTester<DadosDetalhamentoMedico> dadosDetalhamentoMedicoJson;

    // Mock do repository para simular operações no banco sem executá-las de verdade
    @MockBean
    private MedicoRepository repository;

    @Test
    @DisplayName("Deveria devolver codigo http 400 quando informacoes estao invalidas")
    @WithMockUser // Simula usuário autenticado para passar pelo filtro de segurança
    void cadastrar_cenario1() throws Exception {
        // Simula requisição POST para /medicos sem enviar dados no body (JSON vazio)
        var response = mvc
                .perform(post("/medicos"))
                .andReturn().getResponse();

        // Verifica se retornou 400 (Bad Request) porque os dados obrigatórios não foram enviados
        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver codigo http 201 quando informacoes estao validas")
    @WithMockUser // Simula usuário autenticado
    void cadastrar_cenario2() throws Exception {
        // Given: Prepara os dados de cadastro válidos
        var dadosCadastro = new DadosCadastroMedico(
                "Medico",
                "medico@voll.med",
                "61999999999",
                "123456",
                Especialidade.CARDIOLOGIA,
                dadosEndereco());

        // When: Simula o comportamento do repository (retorna o médico salvo)
        when(repository.save(any())).thenReturn(new Medico(dadosCadastro));

        // Simula requisição POST enviando JSON com dados válidos
        var response = mvc
                .perform(post("/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosCadastroMedicoJson.write(dadosCadastro).getJson()))
                .andReturn().getResponse();

        // Prepara o JSON esperado na resposta (dados de detalhamento do médico criado)
        // Nota: id é 0 porque o mock não salva no banco (não gera id real)
        var dadosDetalhamento = new DadosDetalhamentoMedico(
                0L, // id é 0 porque o médico mockado não foi salvo no banco
                dadosCadastro.nome(),
                dadosCadastro.email(),
                dadosCadastro.crm(),
                dadosCadastro.telefone(),
                dadosCadastro.especialidade(),
                new Endereco(dadosCadastro.endereco())
        );
        var jsonEsperado = dadosDetalhamentoMedicoJson.write(dadosDetalhamento).getJson();

        // Then: Verifica se retornou 201 (Created) e o JSON esperado
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    // Método auxiliar: Cria dados de endereço padrão para os testes
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



/*
Nesta aula, você aprendeu como:
Escrever testes automatizados em uma aplicação com Spring Boot;
Escrever testes automatizados de uma interface Repository, seguindo a estratégia de usar o mesmo banco de dados que a aplicação utiliza;
Sobrescrever propriedades do arquivo application.properties, criando outro arquivo chamado application-test.properties que seja carregado apenas ao executar os testes, utilizando para isso a anotação @ActiveProfiles;
Escrever testes automatizados de uma classe Controller, utilizando a classe MockMvc para simular requisições na API;
Testar cenários de erro 400 e código 200 no teste de uma classe controller.



*/