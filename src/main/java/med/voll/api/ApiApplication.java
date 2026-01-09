package med.voll.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

}


//nao precisa adicionar um servidor de aplicaçoes como o tomcat
// no springboot ele ja vem embutido no proprio projeto
// dentro da aplicaçao que vem o servidor
// nao aparece no pom.xml porque ele ja vem herdado da dependencia spring-boot-starter-web
//para rodar a aplicaçao: classe ApiApplication -> Run As -> Java Application como executar via terminal
// ou via linha de comando: mvn spring-boot:run
// o metodo run vai iniciar o servidor embutido (tomcat) e subir a aplicaçao nele
// para testar se a aplicaçao subiu corretamente: abrir o navegador e acessar o endereço http://localhost:8080
// deve aparecer a mensagem Whitelabel Error Page
// isso significa que o servidor esta rodando corretamente, mas nao existe nenhuma rota mapeada
