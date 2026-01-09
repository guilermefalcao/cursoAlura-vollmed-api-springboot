package med.voll.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //serve para indicar que essa classe é um controlador de rotas
@RequestMapping("/hello") //serve para mapear a rota /hello para essa classe
public class HelloController {


@GetMapping //serve para mapear requisiçoes do tipo GET
public String olaMundo() {
    return "Olá Mundo! \n classe helloController.java";

}
}



//esta dizendo ao spring que esta classe é um controller, chegou uma requisicao para /hello do tipo get, ele vai chamar esse metodo olaMundo
//e retornar a string "Olá Mundo!" como resposta da requisicao
//no navegador, ao acessar http://localhost:8080/hello deve aparecer a mensagem "Olá Mundo!"