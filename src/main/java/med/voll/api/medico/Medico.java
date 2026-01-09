package med.voll.api.medico;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
//import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.endereco.Endereco;


@Table(name = "medicos")  //serve para mapear a classe medico com a tabela medicos no banco de dados
@Entity(name = "Medico") //serve para dizer que essa classe é uma entidade jpa  
@Getter //serve para gerar automaticamente os getters para os campos da classe
@NoArgsConstructor //serve para gerar automaticamente o construtor sem argumentos
@AllArgsConstructor //serve para gerar automaticamente o construtor com todos os argumentos
@EqualsAndHashCode (of = "id") //serve para gerar automaticamente os metodos equals e hashcode baseados no campo id
public class Medico {
    //declarar uma classe jpa
    


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) //serve para indicar que o campo id é a chave primaria e que o valor sera gerado automaticamente pelo banco de dados
    private long id;
    
    private String nome;
    private String email;
    private String telefone;
    private String crm;
    
    @Enumerated(EnumType.STRING) //serve para indicar que o campo especialidade é um enum e que o valor sera armazenado como string no banco de dados
    private Especialidade especialidade;
    
    @Embedded //serve para indicar que o campo endereco é um objeto embutido na tabela medicos
    private Endereco endereco;

    private boolean ativo;  //campo para indicar se o medico esta ativo ou inativo, por default é true

    public Medico(DadosCadastroMedico dados) {
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.crm = dados.crm();
        this.especialidade  = dados.especialidade();
        this.endereco = new Endereco(dados.endereco());
        this.ativo = true;  //quando cadastrar um novo medico, ele ja vai estar ativo


}

    public void atualizarInformacoes(DadosAtualizacaoMedico dados) {
        //vai pegar o dto de atualização e atualizar os campos do medico
        if (dados.nome() != null ){
            this.nome = dados.nome();
        }
        if (dados.telefone() != null ){
            this.telefone = dados.telefone();
        }
        if (dados.endereco() != null ){
            this.endereco.atualizarInformacoes(dados.endereco());
        }
    
       
    }

    public void excluir() {
        this.ativo = false;  //marca o medico como inativo
    }


}