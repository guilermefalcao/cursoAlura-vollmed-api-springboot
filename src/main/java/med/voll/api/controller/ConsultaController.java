package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.AgendaDeConsultas;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.DadosCancelamentoConsulta;
import med.voll.api.domain.consulta.DadosDetalhamentoConsulta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;  

@RestController
@RequestMapping("consultas")  //serve para a rota da controller ser /consultas  
@SecurityRequirement(name = "bearer-key") //indica que essa controller requer autenticação via bearer token (JWT)
public class ConsultaController {

    @Autowired
    private  AgendaDeConsultas agenda;  //injetou no controller a classe de servico AgendaDeConsultas para chamar as regras de negocio de agendamento de consultas

    @PostMapping  //vai ser do tipo post
    @Transactional  //serve para garantir que a transação com o banco de dados seja concluída com sucesso
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados) {  //recebe um dto DadosAgendamentoConsulta e valida os dados
        System.out.println(dados);

        var dto = agenda.agendar(dados);  //chama o metodo agendar da classe de servico AgendaDeConsultas passando os dados recebidos da api

        return ResponseEntity.ok(dto); //devolvendo um codigo 200 OK com um dto DadosDetalhamentoConsulta vazio

    }

    @DeleteMapping  //endpoint para cancelar consulta (DELETE)
    @Transactional  //garante que a transação com o banco seja concluída com sucesso
    public ResponseEntity cancelar(@RequestBody @Valid DadosCancelamentoConsulta dados) {
        // Chama o método cancelar da classe de serviço passando os dados de cancelamento
        agenda.cancelar(dados);
        
        // Retorna 204 No Content (sucesso sem conteúdo no corpo da resposta)
        return ResponseEntity.noContent().build();
    }

}    



//DadosAgendamentoConsulta = dto com os dados que vao chegar da api com dados do medico, paciente e data da consulta
//DadosDetalhamentoConsulta = dto com os dados que vao ser devolvidos pela api com detalhes da consulta agendada


//classe controller, controla o fluxo de requisições relacionadas a consultas médicas, 
//exemplo: agendamento de consultas
// controller nao cuida da parte de regras de negocio, apenas do fluxo de requisições e respostas
//fica a cargo de outras camadas (services, repositories) implementar as regras de negocio
//isolar as regras em outra classe que o controller vai chamar