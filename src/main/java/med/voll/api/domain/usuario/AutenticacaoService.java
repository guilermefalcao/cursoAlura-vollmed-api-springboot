package med.voll.api.domain.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {   // UserDetailsService serve para carregar os dados do usuário para autenticação

    @Autowired
    private UsuarioRepository usuarioRepository;  //repositório para buscar usuários no banco

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("DEBUG: Buscando usuário: " + username);
        UserDetails usuario = usuarioRepository.findByLogin(username);
        if (usuario == null) {
            System.out.println("DEBUG: Usuário não encontrado: " + username);
            throw new UsernameNotFoundException("Usuário não encontrado: " + username);
        }
        System.out.println("DEBUG: Usuário encontrado: " + usuario.getUsername());
        System.out.println("DEBUG: Senha hash: " + usuario.getPassword());
        return usuario;
    }

}


//faça uma explicacao do que foi feito:
//Essa classe AutenticacaoService implementa a interface UserDetailsService do Spring Security,
// que é responsável por carregar os dados do usuário para autenticação.
// A anotação @Service indica que essa classe é um serviço gerenciado pelo Spring.
