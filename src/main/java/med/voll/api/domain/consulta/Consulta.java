package med.voll.api.domain.consulta;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.paciente.Paciente;

@Table(name = "consultas") //mapeia para a tabela consultas no banco de dados
@Entity(name = "Consulta") //indica que essa classe é uma entidade JPA
@Getter //gera automaticamente os métodos getters para todos os atributos
@NoArgsConstructor //gera construtor sem argumentos (obrigatório para JPA)
@AllArgsConstructor //gera construtor com todos os argumentos
@EqualsAndHashCode(of = "id") //gera equals e hashCode baseado apenas no id
public class Consulta {

    @Id //indica que esse atributo é a chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) //indica que o valor é gerado automaticamente pelo banco (auto_increment)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) //relacionamento muitos-para-um (várias consultas para um médico)
    @JoinColumn(name = "medico_id") //indica a coluna de junção (foreign key) na tabela consultas
    private Medico medico;

    @ManyToOne(fetch = FetchType.LAZY) //relacionamento muitos-para-um (várias consultas para um paciente)
    @JoinColumn(name = "paciente_id") //indica a coluna de junção (foreign key) na tabela consultas
    private Paciente paciente;

    private LocalDateTime data; //data e hora da consulta

}
