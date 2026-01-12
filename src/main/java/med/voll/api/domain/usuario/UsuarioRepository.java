package med.voll.api.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

// Repository para acessar dados da tabela usuarios
// Estende JpaRepository para operações básicas de CRUD
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Método customizado para buscar usuário por login
    // Usado pelo AutenticacaoService para carregar dados do usuário durante login
    // Retorna UserDetails (interface implementada pela entidade Usuario)
    UserDetails findByLogin(String login);
    
}