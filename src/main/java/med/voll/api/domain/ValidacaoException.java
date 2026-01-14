package med.voll.api.domain;

public class ValidacaoException extends RuntimeException {
    
    public ValidacaoException(String mensagem) {
        super(mensagem);
    }

}

//servirá para lançar exceções de validação de regras de negócio na aplicação