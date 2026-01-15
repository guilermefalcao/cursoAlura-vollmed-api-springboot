package med.voll.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDateTime;

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

import med.voll.api.domain.consulta.AgendaDeConsultas;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.DadosDetalhamentoConsulta;
import med.voll.api.domain.medico.Especialidade;


// Carrega o contexto completo do Spring (diferente de @WebMvcTest que carrega só o controller)
@SpringBootTest
// Configura o MockMvc para simular requisições HTTP
@AutoConfigureMockMvc
// Configura os conversores JSON para serializar/deserializar objetos nos testes
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    // Injeta o MockMvc que simula requisições HTTP sem subir o servidor
    @Autowired
    private MockMvc mvc;

    // Injeta o JacksonTester para serializar/deserializar objetos JSON nos testes
    @Autowired
    private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsultaJson;

    @Autowired
    private JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoConsultaJson;

    // Mock da classe de serviço para simular o comportamento sem executar a lógica real
    @MockBean
    private AgendaDeConsultas agendaDeConsultas;



    @Test
    @DisplayName("Deveria devolver codigo http 400 quando informacoes estao invalidas")
    @WithMockUser // Simula um usuário autenticado no Spring Security para passar pelo filtro JWT, simula um usuario logado
    void agendar_cenario1() throws Exception {
        // Simula uma requisição POST para /consultas sem enviar dados no body (JSON vazio)
        var response = mvc
                .perform(post("/consultas"))
                .andReturn().getResponse();

        // Verifica se o status HTTP retornado é 400 (Bad Request)
        // Isso acontece porque o Bean Validation detecta que os dados obrigatórios não foram enviados
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }


    @Test
    @DisplayName("Deveria devolver codigo http 200 quando informacoes estao validas")
    @WithMockUser // Simula usuário autenticado
    void agendar_cenario2() throws Exception {
        // Given: Prepara os dados de entrada e saída
        var data = LocalDateTime.now().plusDays(1).withHour(10).withMinute(0);
        var especialidade = Especialidade.CARDIOLOGIA;
        var dadosDetalhamento = new DadosDetalhamentoConsulta(null, 2l, 5l, data);

        // When: Simula o comportamento do serviço (retorna o detalhamento)
        when(agendaDeConsultas.agendar(any())).thenReturn(dadosDetalhamento);

        // Simula requisição POST enviando JSON com dados válidos
        var response = mvc
                .perform(
                    post("/consultas")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(dadosAgendamentoConsultaJson.write(
                        new DadosAgendamentoConsulta(2l, 5l, data, especialidade)
                    ).getJson())
                )
                .andReturn().getResponse();

        // Then: Verifica se retornou 200 e o JSON esperado
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        
        var jsonEsperado = dadosDetalhamentoConsultaJson.write(dadosDetalhamento).getJson();
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }
}
