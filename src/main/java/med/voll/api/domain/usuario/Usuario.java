package med.voll.api.domain.usuario;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "usuarios")  //serve para mapear a classe Usuario com a tabela usuarios no banco de dados
@Entity(name = "Usuario") //serve para dizer que essa classe é uma entidade JPA  
@Getter //serve para gerar automaticamente os getters para os campos da classe
@NoArgsConstructor //serve para gerar automaticamente o construtor sem argumentos
@AllArgsConstructor //serve para gerar automaticamente o construtor com todos os argumentos
@EqualsAndHashCode(of = "id") //serve para gerar automaticamente os métodos equals e hashcode baseados no campo id
public class Usuario implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) //serve para indicar que o campo id é a chave primária e que o valor será gerado automaticamente pelo banco de dados
    private Long id;
    private String login;
    private String senha;

    // Métodos obrigatórios da interface UserDetails do Spring Security
    // Estes métodos definem as regras de negócio para autenticação e autorização
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Retorna as permissões/roles do usuário
        // Por enquanto, todos os usuários têm role "ROLE_USER"
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        // Retorna a senha do usuário (deve estar criptografada)
        return senha;
    }

    @Override
    public String getUsername() {
        // Retorna o nome de usuário (login)
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        // Indica se a conta do usuário não está expirada
        return true; // Nossa aplicação não expira contas
    }

    @Override
    public boolean isAccountNonLocked() {
        // Indica se a conta do usuário não está bloqueada
        return true; // Nossa aplicação não bloqueia contas
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Indica se as credenciais (senha) não estão expiradas
        return true; // Nossa aplicação não expira senhas
    }

    @Override
    public boolean isEnabled() {
        // Indica se o usuário está habilitado/ativo
        return true; // Todos os usuários estão ativos
    }

}