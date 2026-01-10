package med.voll.api.domain.paciente;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.endereco.Endereco;

@Getter //serve para gerar automaticamente os getters para os campos da classe
@EqualsAndHashCode(of = "id") //serve para gerar automaticamente os metodos equals e hashcode baseados no campo id
@NoArgsConstructor //serve para gerar automaticamente o construtor sem argumentos
@AllArgsConstructor //serve para gerar automaticamente o construtor com todos os argumentos
@Entity(name = "Paciente") //serve para dizer que essa classe é uma entidade jpa
@Table(name = "pacientes") //serve para mapear a classe paciente com a tabela pacientes no banco de dados
public class Paciente {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) //serve para indicar que o campo id é a chave primaria e que o valor sera gerado automaticamente pelo banco de dados
    private Long id;

    private String nome;
    private String email;
    private String cpf;
    private String telefone;
    private Boolean ativo; //campo para exclusão lógica - true=ativo, false=inativo

    @Embedded //serve para indicar que o campo endereco é um objeto embutido na tabela pacientes
    private Endereco endereco;

    public Paciente(DadosCadastroPaciente dados) {
        this.ativo = true; //todo paciente começa ativo
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.cpf = dados.cpf();
        this.endereco = new Endereco(dados.endereco());
    }

    //método para atualizar informações do paciente
    //só atualiza os campos que não são null (atualização parcial)
    public void atualizarInformacoes(DadosAtualizacaoPaciente dados) {
        if (dados.nome() != null)
            this.nome = dados.nome();

        if (dados.telefone() != null)
            this.telefone = dados.telefone();

        if (dados.endereco() != null)
            endereco.atualizarInformacoes(dados.endereco());
    }

    //método para exclusão lógica - marca como inativo em vez de deletar do banco
    public void inativar() {
        this.ativo = false;
    }

}
