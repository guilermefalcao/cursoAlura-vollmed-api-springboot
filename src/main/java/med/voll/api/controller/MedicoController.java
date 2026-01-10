package med.voll.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

//import java.net.URI;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.medico.DadosAtualizacaoMedico;
import med.voll.api.domain.medico.DadosCadastroMedico;
import med.voll.api.domain.medico.DadosDetalhamentoMedico;
import med.voll.api.domain.medico.DadosListagemMedico;
import med.voll.api.domain.medico.DadosListagemMedicoNome;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController //serve para indicar que essa classe é um controlador de rotas
@RequestMapping("/medicos") //serve para mapear a rota /medicos para essa classe
public class MedicoController {

@Autowired //injeçao de dependencia do repositorio de medicos
private MedicoRepository repository; 
//esta dizendo ao spring que esta classe é um controller

    @PostMapping //serve para mapear requisições do tipo POST
    @Transactional //serve para indicar que esse método deve ser executado dentro de uma transação do banco de dados
    public ResponseEntity<DadosDetalhamentoMedico> cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder) {
        var medico = new Medico(dados);
        repository.save(medico);

        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico)); //201 Created com dados do médico criado
        // tem que devolver um json no corpo e devolver os dados do cabeçalho Location
        // cadastrar agora utiliza o DTO DadosDetalhamentoMedico para retornar os dados do médico criado


    }


    //método para listar, no CRUD é o Read
    @GetMapping //serve para mapear requisições do tipo GET, carregando dados
    public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(size = 11, sort = {"nome"}) Pageable paginacao) {
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
        return ResponseEntity.ok(page); //200 OK com os dados da página
    } 

    //metodo para listar apenas os nomes dos médicos
    @GetMapping("/nomes") //rota específica para listar apenas nomes
    public ResponseEntity<List<DadosListagemMedicoNome>> listarNomes(@PageableDefault(size = 10, sort = {"nome"}, direction = Sort.Direction.ASC) Pageable paginacao) {
        var lista = repository.findAll(paginacao).map(DadosListagemMedicoNome::new).toList();
        return ResponseEntity.ok(lista); //200 OK com a lista de nomes
    } 

    @PutMapping //serve para mapear requisições do tipo PUT - atualizar dados
    @Transactional //serve para indicar que esse método deve ser executado dentro de uma transação do banco de dados
    public ResponseEntity<DadosDetalhamentoMedico> atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico)); //200 OK com dados atualizados
    }


    @DeleteMapping("/{id}") //serve para mapear requisiçoes do tipo DELETE - deletar dados
    @Transactional  //vai fazer uma escrita no banco de dados
    public ResponseEntity excluir (@PathVariable Long id) {  // Pode retornar 204 No Content (mais semântico) na boa pratica 204 "Operação realizada com sucesso, mas não há conteúdo para retornar"
         var medico = repository.getReferenceById((id));  //serve para  pegar a referencia do medico no banco de dados
         medico.excluir();  //chama o metodo excluir do medico para marcar como inativo

        return ResponseEntity.noContent().build();


    }


    @GetMapping("/{id}") //serve para mapear requisiçoes do tipo GET - para tetalhar medico por id
    public ResponseEntity detalhar (@PathVariable Long id) {  // Pode retornar 204 No Content (mais semântico) na boa pratica 204 "Operação realizada com sucesso, mas não há conteúdo para retornar"
         var medico = repository.getReferenceById((id));  //serve para  pegar a referencia do medico no banco de dados
         

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));  //vai passasr os dados do medico que foi buscado no banco de dados


    }




}


//nao é a melhor forma porque esta imprimindo uma string json
//o ideal seria criar uma classe MedicoCadastroDTO para receber os dados do medico  
//e o spring faria automaticamente o mapeamento do json para o objeto dessa classe
//assim poderiamos acessar os dados do medico de forma mais facil e segura  

// no exemplo feit com string vai receber o corpo da requisicao como uma string bruta
// e imprimir no console    


//como verificar a query q o spring faz no banco?
// no arquivo application.properties adicionar a linha abaixo:
// spring.jpa.show-sql=true