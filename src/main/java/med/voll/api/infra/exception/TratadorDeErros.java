package med.voll.api.infra.exception;

import java.lang.reflect.Method;





import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice //serve para tratar erros de forma global em todos os controllers
public class TratadorDeErros {


    //para o erro 404 Not Found, vamos criar um método aqui
    @ExceptionHandler(EntityNotFoundException.class) //serve para tratar a exceção EntityNotFoundException  
    public ResponseEntity tratarErro404() {
        return ResponseEntity.notFound().build(); //retorna 404 Not Found
        
    }


    //para o erro 400 Bad Request, vamos criar outro método aqui
    // exemplo no cadastro de medico ou paciente no INSOMNIA
    @ExceptionHandler(MethodArgumentNotValidException.class) //serve para tratar a exceção MethodArgumentNotValidException
    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex) {
        var erros = ex.getFieldErrors(); //pega a lista de erros de validação
        //podemos criar um DTO para retornar os erros de forma estruturada
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList()); //retorna 400 Bad Request
        
    }

    //declarando o record:
    private record DadosErroValidacao(String campo, String mensagem) {

        public DadosErroValidacao(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }


}



//o spring sabe que em qualquer controle do nosso projeto for lançada a exceção EntityNotFoundException
//ele deve chamar esse método tratarErro404 dessa classe TratadorDeErros
//e esse método vai tratar o erro de forma personalizada    

// no GET para detalhar medico, se passar um id que nao existe,
//o spring lança a exceção EntityNotFoundException
//então o spring vai chamar esse método tratarErro404   
