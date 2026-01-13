# MANUAL DO PROJETO - VOLL.MED API
## Sistema de Gestão Médica com Spring Boot

---

## 📋 ÍNDICE
1. [Visão Geral do Projeto](#visão-geral)
2. [Tecnologias Utilizadas](#tecnologias)
3. [Estrutura do Projeto](#estrutura)
4. [Configuração do Ambiente](#configuração)
5. [Entidades e Domínio](#entidades)
6. [Controllers e APIs](#controllers)
7. [Sistema de Autenticação JWT](#autenticação)
8. [Segurança com Spring Security](#segurança)
9. [Tratamento de Erros](#erros)
10. [Banco de Dados e Migrações](#banco)
11. [Como Testar a API](#testes)
12. [Estrutura de Pastas](#pastas)

---

## 🎯 VISÃO GERAL DO PROJETO {#visão-geral}

O **Voll.med API** é um sistema de gestão médica desenvolvido em Spring Boot que permite:

- ✅ **Cadastro e gerenciamento de médicos**
- ✅ **Cadastro e gerenciamento de pacientes**
- ✅ **Sistema de autenticação JWT**
- ✅ **Controle de acesso com Spring Security**
- ✅ **Validações personalizadas**
- ✅ **Paginação e ordenação**
- ✅ **Tratamento global de erros**

### Funcionalidades Principais:
- **CRUD completo** para médicos e pacientes
- **Autenticação segura** com tokens JWT
- **Validação de dados** com Bean Validation
- **Soft delete** (exclusão lógica)
- **Paginação** automática de resultados
- **Tratamento de exceções** centralizado

---

## 🛠️ TECNOLOGIAS UTILIZADAS {#tecnologias}

### Backend:
- **Java 17** - Linguagem de programação
- **Spring Boot 3.5.9** - Framework principal
- **Spring Data JPA** - Persistência de dados
- **Spring Security** - Segurança e autenticação
- **Spring Validation** - Validação de dados
- **MySQL 8.0** - Banco de dados
- **Flyway** - Controle de versão do banco
- **Maven** - Gerenciamento de dependências

### Bibliotecas Específicas:
- **java-jwt (Auth0)** - Geração e validação de tokens JWT
- **BCrypt** - Criptografia de senhas
- **Lombok** - Redução de código boilerplate
- **DevTools** - Hot reload em desenvolvimento

---

## 🏗️ ESTRUTURA DO PROJETO {#estrutura}

```
api/
├── src/main/java/med/voll/api/
│   ├── controller/          # Controllers REST
│   ├── domain/             # Entidades de domínio
│   │   ├── endereco/       # Classe Endereco
│   │   ├── medico/         # Entidade Medico + DTOs
│   │   ├── paciente/       # Entidade Paciente + DTOs
│   │   └── usuario/        # Entidade Usuario + Service
│   └── infra/              # Infraestrutura
│       ├── exception/      # Tratamento de erros
│       └── security/       # Configurações de segurança
├── src/main/resources/
│   ├── db/migration/       # Scripts Flyway
│   ├── application.properties
│   └── ValidationMessages.properties
└── pom.xml                 # Dependências Maven
```

---

## ⚙️ CONFIGURAÇÃO DO AMBIENTE {#configuração}

### 1. Pré-requisitos:
- **Java 17** instalado
- **MySQL 8.0** rodando
- **Maven** configurado
- **IDE** (IntelliJ, Eclipse, VS Code)

### 2. Configuração do Banco:
```sql
CREATE DATABASE vollmed_api;
CREATE USER 'vollmed'@'localhost' IDENTIFIED BY 'vollmed123';
GRANT ALL PRIVILEGES ON vollmed_api.* TO 'vollmed'@'localhost';
```

### 3. Configuração da Aplicação:
```properties
# application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/vollmed_api?allowPublicKeyRetrieval=true&useSSL=false
spring.datasource.username=vollmed
spring.datasource.password=vollmed123
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

server.error.include-stacktrace=never
api.security.token.secret=12345678
```

### 4. Executar o Projeto:
```bash
mvn spring-boot:run
```

---

## 🏥 ENTIDADES E DOMÍNIO {#entidades}

### 1. Entidade Medico:
```java
@Entity
@Table(name = "medicos")
public class Medico {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nome;
    private String email;
    private String telefone;
    private String crm;
    
    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;
    
    @Embedded
    private Endereco endereco;
    
    private Boolean ativo;
}
```

### 2. Entidade Paciente:
```java
@Entity
@Table(name = "pacientes")
public class Paciente {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    
    @Embedded
    private Endereco endereco;
    
    private Boolean ativo;
}
```

### 3. Classe Endereco (Embeddable):
```java
@Embeddable
public class Endereco {
    private String logradouro;
    private String bairro;
    private String cep;
    private String numero;
    private String complemento;
    private String cidade;
    private String uf;
}
```

### 4. Entidade Usuario (Autenticação):
```java
@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String login;
    private String senha;
    
    // Implementação dos métodos UserDetails
}
```

---

## 🌐 CONTROLLERS E APIs {#controllers}

### 1. MedicoController:
```java
@RestController
@RequestMapping("medicos")
public class MedicoController {
    
    @PostMapping
    public ResponseEntity<DadosDetalhamentoMedico> cadastrar(@RequestBody @Valid DadosCadastroMedico dados) {
        // Cadastra novo médico
    }
    
    @GetMapping
    public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        // Lista médicos com paginação
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoMedico> detalhar(@PathVariable Long id) {
        // Detalha médico específico
    }
    
    @PutMapping
    public ResponseEntity<DadosDetalhamentoMedico> atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
        // Atualiza dados do médico
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable Long id) {
        // Exclusão lógica (soft delete)
    }
}
```

### 2. PacienteController:
- Estrutura similar ao MedicoController
- CRUD completo para pacientes
- Mesmos padrões de paginação e validação

### 3. AutenticacaoController:
```java
@RestController
@RequestMapping("/login")
public class AutenticacaoController {
    
    @PostMapping
    public ResponseEntity<DadosTokenJWT> efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
        // Autentica usuário e retorna token JWT
    }
}
```

---

## 🔐 SISTEMA DE AUTENTICAÇÃO JWT {#autenticação}

### 1. TokenService:
```java
@Service
public class TokenService {
    private String secret = "12345678";
    
    public String gerarToken(Usuario usuario) {
        return JWT.create()
            .withIssuer("API Voll.med")
            .withSubject(usuario.getLogin())
            .withExpiresAt(dataExpiracao())
            .sign(Algorithm.HMAC256(secret));
    }
    
    public String getSubject(String tokenJWT) {
        return JWT.require(Algorithm.HMAC256(secret))
            .withIssuer("API Voll.med")
            .build()
            .verify(tokenJWT)
            .getSubject();
    }
    
    private Instant dataExpiracao() {
        return LocalDateTime.now()
            .plusHours(2)
            .toInstant(ZoneOffset.of("-03:00"));
    }
}
```

### 2. Fluxo de Autenticação:
1. **Login**: POST `/login` com email/senha
2. **Validação**: BCrypt verifica senha
3. **Token**: JWT gerado com 2h de validade
4. **Uso**: Bearer Token em todas as requisições protegidas

---

## 🛡️ SEGURANÇA COM SPRING SECURITY {#segurança}

### 1. SecurityConfigurations:
```java
@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(req -> {
                req.requestMatchers(HttpMethod.POST, "/login").permitAll();
                req.anyRequest().authenticated();
            })
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }
}
```

### 2. SecurityFilter (Filtro Customizado):
```java
@Component
public class SecurityFilter extends OncePerRequestFilter {
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        var tokenJWT = recuperarToken(request);
        
        if (tokenJWT != null) {
            try {
                var subject = tokenService.getSubject(tokenJWT);
                var usuario = repository.findByLogin(subject);
                
                var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (RuntimeException e) {
                // Token inválido - não autentica
            }
        }
        
        filterChain.doFilter(request, response);
    }
}
```

### 3. Ordem dos Filtros:
1. **SecurityFilter** → Valida JWT e autentica usuário
2. **UsernamePasswordAuthenticationFilter** → Verifica se está autenticado

---

## ⚠️ TRATAMENTO DE ERROS {#erros}

### TratadorDeErros (Global Exception Handler):
```java
@RestControllerAdvice
public class TratadorDeErros {
    
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404() {
        return ResponseEntity.notFound().build();
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex) {
        var erros = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
    }
    
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity tratarErroBadCredentials() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
    }
    
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity tratarErroAcessoNegado() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado");
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity tratarErro500(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + ex.getLocalizedMessage());
    }
}
```

### Tipos de Erro Tratados:
- **404** - Recurso não encontrado
- **400** - Dados inválidos ou JSON malformado
- **401** - Credenciais inválidas ou falha na autenticação
- **403** - Acesso negado
- **500** - Erro interno do servidor

---

## 🗄️ BANCO DE DADOS E MIGRAÇÕES {#banco}

### Migrações Flyway:

#### V1 - Criar tabela médicos:
```sql
CREATE TABLE medicos(
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    crm VARCHAR(6) NOT NULL UNIQUE,
    especialidade VARCHAR(100) NOT NULL,
    logradouro VARCHAR(100) NOT NULL,
    bairro VARCHAR(100) NOT NULL,
    cep VARCHAR(9) NOT NULL,
    complemento VARCHAR(100),
    numero VARCHAR(20),
    uf CHAR(2) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    PRIMARY KEY(id)
);
```

#### V2 - Criar tabela pacientes:
```sql
CREATE TABLE pacientes(
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    logradouro VARCHAR(100) NOT NULL,
    bairro VARCHAR(100) NOT NULL,
    cep VARCHAR(9) NOT NULL,
    complemento VARCHAR(100),
    numero VARCHAR(20),
    uf CHAR(2) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    PRIMARY KEY(id)
);
```

#### V3 - Adicionar campo telefone:
```sql
ALTER TABLE medicos ADD telefone VARCHAR(20) NOT NULL;
ALTER TABLE pacientes ADD telefone VARCHAR(20) NOT NULL;
```

#### V4 - Adicionar campo ativo:
```sql
ALTER TABLE medicos ADD ativo TINYINT;
ALTER TABLE pacientes ADD ativo TINYINT;
UPDATE medicos SET ativo = 1;
UPDATE pacientes SET ativo = 1;
```

#### V5 - Criar tabela usuários:
```sql
CREATE TABLE usuarios(
    id BIGINT NOT NULL AUTO_INCREMENT,
    login VARCHAR(100) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    PRIMARY KEY(id)
);
```

#### V6 - Inserir usuário padrão:
```sql
INSERT INTO usuarios(login, senha) VALUES('ana.souza@voll.med', '$2a$10$Y50UaMFOxteibQEYLrwuHeehHYfcoafCopUazP12.rqB41bsolF5.');
```

---

## 🧪 COMO TESTAR A API {#testes}

### 1. Fazer Login:
```http
POST http://localhost:8080/login
Content-Type: application/json

{
    "login": "ana.souza@voll.med",
    "senha": "123456"
}
```

**Resposta:**
```json
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### 2. Cadastrar Médico:
```http
POST http://localhost:8080/medicos
Authorization: Bearer {token}
Content-Type: application/json

{
    "nome": "Dr. João Silva",
    "email": "joao@voll.med",
    "telefone": "11999999999",
    "crm": "123456",
    "especialidade": "CARDIOLOGIA",
    "endereco": {
        "logradouro": "Rua das Flores, 123",
        "bairro": "Centro",
        "cep": "01234-567",
        "cidade": "São Paulo",
        "uf": "SP",
        "numero": "123",
        "complemento": "Apto 45"
    }
}
```

### 3. Listar Médicos:
```http
GET http://localhost:8080/medicos?page=0&size=5&sort=nome,asc
Authorization: Bearer {token}
```

### 4. Atualizar Médico:
```http
PUT http://localhost:8080/medicos
Authorization: Bearer {token}
Content-Type: application/json

{
    "id": 1,
    "nome": "Dr. João Santos",
    "telefone": "11888888888"
}
```

### 5. Excluir Médico (Soft Delete):
```http
DELETE http://localhost:8080/medicos/1
Authorization: Bearer {token}
```

---

## 📁 ESTRUTURA DE PASTAS DETALHADA {#pastas}

```
api/
├── src/
│   ├── main/
│   │   ├── java/med/voll/api/
│   │   │   ├── ApiApplication.java                    # Classe principal
│   │   │   ├── controller/
│   │   │   │   ├── AutenticacaoController.java        # Login/JWT
│   │   │   │   ├── MedicoController.java              # CRUD médicos
│   │   │   │   └── PacienteController.java            # CRUD pacientes
│   │   │   ├── domain/
│   │   │   │   ├── endereco/
│   │   │   │   │   └── Endereco.java                  # Classe embeddable
│   │   │   │   ├── medico/
│   │   │   │   │   ├── Medico.java                    # Entidade
│   │   │   │   │   ├── MedicoRepository.java          # Repository
│   │   │   │   │   ├── Especialidade.java             # Enum
│   │   │   │   │   ├── DadosCadastroMedico.java       # DTO cadastro
│   │   │   │   │   ├── DadosListagemMedico.java       # DTO listagem
│   │   │   │   │   ├── DadosAtualizacaoMedico.java    # DTO atualização
│   │   │   │   │   └── DadosDetalhamentoMedico.java   # DTO detalhamento
│   │   │   │   ├── paciente/
│   │   │   │   │   ├── Paciente.java                  # Entidade
│   │   │   │   │   ├── PacienteRepository.java        # Repository
│   │   │   │   │   ├── DadosCadastroPaciente.java     # DTO cadastro
│   │   │   │   │   ├── DadosListagemPaciente.java     # DTO listagem
│   │   │   │   │   ├── DadosAtualizacaoPaciente.java  # DTO atualização
│   │   │   │   │   └── DadosDetalhamentoPaciente.java # DTO detalhamento
│   │   │   │   └── usuario/
│   │   │   │       ├── Usuario.java                   # Entidade UserDetails
│   │   │   │       ├── UsuarioRepository.java         # Repository
│   │   │   │       ├── AutenticacaoService.java       # UserDetailsService
│   │   │   │       └── DadosAutenticacao.java         # DTO login
│   │   │   └── infra/
│   │   │       ├── exception/
│   │   │       │   └── TratadorDeErros.java           # Global exception handler
│   │   │       └── security/
│   │   │           ├── SecurityConfigurations.java    # Config Spring Security
│   │   │           ├── SecurityFilter.java            # Filtro JWT customizado
│   │   │           ├── TokenService.java              # Geração/validação JWT
│   │   │           └── DadosTokenJWT.java             # DTO token response
│   │   └── resources/
│   │       ├── db/migration/
│   │       │   ├── V1__create-table-medicos.sql
│   │       │   ├── V2__create-table-pacientes.sql
│   │       │   ├── V3__add-telefone-medicos-pacientes.sql
│   │       │   ├── V4__add-ativo-medicos-pacientes.sql
│   │       │   ├── V5__create-table-usuarios.sql
│   │       │   └── V6__insert-usuario-admin.sql
│   │       ├── application.properties                 # Configurações principais
│   │       └── ValidationMessages.properties          # Mensagens de validação
│   └── test/                                          # Testes (não implementados)
├── target/                                            # Arquivos compilados
├── pom.xml                                            # Dependências Maven
├── README.md                                          # Documentação do projeto
└── MANUAL_PROJETO_VOLLMED_API.md                     # Este manual
```

---

## 🎯 CONCLUSÃO

O projeto **Voll.med API** demonstra uma implementação completa de uma API REST com Spring Boot, incluindo:

### ✅ **Funcionalidades Implementadas:**
- Sistema de autenticação JWT robusto
- CRUD completo com validações
- Segurança com Spring Security
- Tratamento global de exceções
- Paginação e ordenação
- Soft delete para preservar dados
- Migrações de banco controladas

### 🚀 **Próximos Passos Possíveis:**
- Implementar testes unitários e de integração
- Adicionar documentação com Swagger/OpenAPI
- Implementar sistema de agendamento de consultas
- Adicionar logs estruturados
- Implementar cache com Redis
- Adicionar métricas e monitoramento

### 📚 **Conceitos Aprendidos:**
- Arquitetura REST com Spring Boot
- Autenticação e autorização com JWT
- Padrões de segurança web
- Validação de dados
- Persistência com JPA/Hibernate
- Controle de versão de banco com Flyway
- Tratamento de exceções
- Boas práticas de desenvolvimento

---

**Desenvolvido seguindo o curso Alura - Spring Boot 3**  
**Projeto: Sistema de Gestão Médica Voll.med**  
**Data: Janeiro 2026**