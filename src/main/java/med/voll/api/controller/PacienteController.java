package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.paciente.DadosAtualizacaoPaciente;
import med.voll.api.paciente.DadosCadastroPaciente;
import med.voll.api.paciente.DadosDetalhamentoPaciente;
import med.voll.api.paciente.DadosListagemPaciente;
import med.voll.api.paciente.Paciente;
import med.voll.api.paciente.PacienteRepository;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController //serve para indicar que essa classe é um controlador de rotas
@RequestMapping("/pacientes") //serve para mapear a rota /pacientes para essa classe
public class PacienteController {

    @Autowired //injeção de dependência do repositório de pacientes
    private PacienteRepository repository;

    @PostMapping //serve para mapear requisições do tipo POST
    @Transactional //serve para indicar que esse método deve ser executado dentro de uma transação do banco de dados
    public ResponseEntity<DadosDetalhamentoPaciente> cadastrar(@RequestBody @Valid DadosCadastroPaciente dados, UriComponentsBuilder uriBuilder) {
        //cria novo paciente e salva no banco de dados
        var paciente = new Paciente(dados);
        repository.save(paciente);
        
        //cria URI do recurso criado para o header Location
        var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        //retorna 201 Created com header Location e dados do paciente criado no body
        return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(paciente));
        //ROTA INSOMNIA: POST http://localhost:8080/pacientes
    }

    //método para listar pacientes com paginação, no CRUD é o Read
    @GetMapping //serve para mapear requisições do tipo GET, carregando dados
    public ResponseEntity<Page<DadosListagemPaciente>> listar(@PageableDefault(page = 0, size = 10, sort = {"nome"}) Pageable paginacao) {
        //busca apenas pacientes ativos e transforma em DTO de listagem
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemPaciente::new);
        //retorna 200 OK com página de pacientes
        return ResponseEntity.ok(page);
        //ROTA INSOMNIA: GET http://localhost:8080/pacientes
    }

    //método para detalhar um paciente específico, no CRUD é o Read por ID
    @GetMapping("/{id}") //serve para mapear requisições do tipo GET com parâmetro de rota
    public ResponseEntity<DadosDetalhamentoPaciente> detalhar(@PathVariable Long id) {
        //busca o paciente pelo ID e obtém uma referência do JPA
        var paciente = repository.getReferenceById(id);
        //retorna 200 OK com todos os dados do paciente
        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
        //ROTA INSOMNIA: GET http://localhost:8080/pacientes/1
    }

    //método para atualizar paciente, no CRUD é o Update
    @PutMapping //serve para mapear requisições do tipo PUT - atualizar dados
    @Transactional //serve para indicar que esse método deve ser executado dentro de uma transação do banco de dados
    public ResponseEntity<DadosDetalhamentoPaciente> atualizar(@RequestBody @Valid DadosAtualizacaoPaciente dados) {
        //busca o paciente pelo ID e obtém uma referência (proxy) do JPA
        var paciente = repository.getReferenceById(dados.id());
        //chama o método de atualização na entidade
        paciente.atualizarInformacoes(dados);
        //retorna 200 OK com dados atualizados do paciente
        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
        //ROTA INSOMNIA: PUT http://localhost:8080/pacientes
    }

    //método para exclusão lógica de paciente, no CRUD é o Delete
    @DeleteMapping("/{id}") //serve para mapear requisições do tipo DELETE - {id} captura o parâmetro da URL
    @Transactional //vai fazer uma escrita no banco de dados
    public ResponseEntity remover(@PathVariable Long id) {
        //busca o paciente pelo ID e obtém uma referência (proxy) do JPA
        var paciente = repository.getReferenceById(id);
        //chama o método de inativação na entidade (exclusão lógica)
        paciente.inativar();
        //retorna 204 No Content - operação realizada com sucesso, sem conteúdo para retornar
        return ResponseEntity.noContent().build();
        //ROTA INSOMNIA: DELETE http://localhost:8080/pacientes/1
    }

}
