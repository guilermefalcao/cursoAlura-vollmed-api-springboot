# 🏥 Voll.med API - Manual Completo do Projeto

## 📋 Índice
1. [Sobre o Projeto](#sobre-o-projeto)
2. [Tecnologias Utilizadas](#tecnologias-utilizadas)
3. [Arquitetura do Projeto](#arquitetura-do-projeto)
4. [Configuração do Ambiente](#configuração-do-ambiente)
5. [Banco de Dados](#banco-de-dados)
6. [Funcionalidades](#funcionalidades)
7. [Segurança e Autenticação](#segurança-e-autenticação)
8. [Documentação da API (Swagger)](#documentação-da-api-swagger)
9. [Testes Automatizados](#testes-automatizados)
10. [Build e Deploy](#build-e-deploy)
11. [Como Executar](#como-executar)

---

## 📖 Sobre o Projeto

**Voll.med** é uma API REST desenvolvida em Spring Boot para gerenciar uma clínica médica. O sistema permite:
- Cadastro e gerenciamento de médicos
- Cadastro e gerenciamento de pacientes
- Agendamento de consultas com validações de regras de negócio
- Cancelamento de consultas
- Autenticação e autorização com JWT
- Documentação interativa com Swagger

**Curso:** Alura - Spring Boot 3
**Versão:** 0.0.1-SNAPSHOT

---

## 🛠️ Tecnologias Utilizadas

### Backend
- **Java 17** - Linguagem de programação
- **Spring Boot 3.5.9** - Framework principal
- **Spring Data JPA** - Persistência de dados
- **Spring Security** - Segurança e autenticação
- **Spring Validation** - Validação de dados

### Banco de Dados
- **MySQL 8.0** - Banco de dados relacional
- **Flyway** - Versionamento e migração de banco de dados
- **H2 Database** - Banco em memória para testes

### Segurança
- **JWT (JSON Web Token)** - Autenticação stateless
- **Auth0 Java JWT 4.5.0** - Biblioteca para geração de tokens
- **BCrypt** - Criptografia de senhas

### Documentação
- **SpringDoc OpenAPI 2.8.15** - Geração automática de documentação
- **Swagger UI** - Interface interativa para testar a API

### Ferramentas
- **Lombok** - Redução de código boilerplate
- **Maven** - Gerenciamento de dependências
- **DevTools** - Hot reload em desenvolvimento

### Testes
- **JUnit 5** - Framework de testes
- **Spring Boot Test** - Testes de integração
- **Spring Security Test** - Testes de segurança

---

## 🏗️ Arquitetura do Projeto

```
api/
├── src/
│   ├── main/
│   │   ├── java/med/voll/api/
│   │   │   ├── controller/          # Controladores REST
│   │   │   │   ├── AutenticacaoController.java
│   │   │   │   ├── ConsultaController.java
│   │   │   │   ├── MedicoController.java
│   │   │   │   └── PacienteController.java
│   │   │   │
│   │   │   ├── domain/              # Camada de domínio
│   │   │   │   ├── consulta/        # Entidades e regras de consulta
│   │   │   │   │   ├── validacoes/  # Validadores de negócio
│   │   │   │   │   ├── Consulta.java
│   │   │   │   │   ├── ConsultaRepository.java
│   │   │   │   │   └── AgendaDeConsultas.java
│   │   │   │   │
│   │   │   │   ├── medico/          # Entidades de médico
│   │   │   │   │   ├── Medico.java
│   │   │   │   │   ├── MedicoRepository.java
│   │   │   │   │   └── Especialidade.java
│   │   │   │   │
│   │   │   │   ├── paciente/        # Entidades de paciente
│   │   │   │   │   ├── Paciente.java
│   │   │   │   │   └── PacienteRepository.java
│   │   │   │   │
│   │   │   │   ├── usuario/         # Autenticação
│   │   │   │   │   ├── Usuario.java
│   │   │   │   │   └── AutenticacaoService.java
│   │   │   │   │
│   │   │   │   └── endereco/        # Value Object
│   │   │   │       └── Endereco.java
│   │   │   │
│   │   │   ├── infra/               # Infraestrutura
│   │   │   │   ├── security/        # Configurações de segurança
│   │   │   │   │   ├── SecurityConfigurations.java
│   │   │   │   │   ├── SecurityFilter.java
│   │   │   │   │   └── TokenService.java
│   │   │   │   │
│   │   │   │   ├── exception/       # Tratamento de erros
│   │   │   │   │   └── TratadorDeErros.java
│   │   │   │   │
│   │   │   │   └── springdoc/       # Configuração Swagger
│   │   │   │       └── SpringDocConfiguration.java
│   │   │   │
│   │   │   └── ApiApplication.java  # Classe principal
│   │   │
│   │   └── resources/
│   │       ├── db/migration/        # Scripts Flyway
│   │       │   ├── V1__create-table-medicos.sql
│   │       │   ├── V2__alter-table-medicos-add-column-telefone.sql
│   │       │   ├── V3__create-table-pacientes.sql
│   │       │   ├── V4__alter-table-medicos-add-column-ativo.sql
│   │       │   ├── V5__alter-table-pacientes-add-column-ativo.sql
│   │       │   ├── V6__create-table-usuarios.sql
│   │       │   ├── V7__create-table-consultas.sql
│   │       │   └── V8__alter-table-consultas-add-motivo-cancelamento.sql
│   │       │
│   │       ├── application.properties           # Configuração desenvolvimento
│   │       ├── application-prod.properties      # Configuração produção
│   │       └── ValidationMessages.properties    # Mensagens de validação
│   │
│   └── test/
│       ├── java/med/voll/api/
│       │   ├── controller/
│       │   │   ├── MedicoControllerTest.java
│       │   │   └── ConsultaControllerTest.java
│       │   └── ApiApplicationTests.java
│       │
│       └── resources/
│           └── application-test.properties      # Configuração testes
│
├── pom.xml                          # Dependências Maven
└── README.md                        # Este arquivo
```

---

## ⚙️ Configuração do Ambiente

### Pré-requisitos
- **Java 17** ou superior
- **Maven 3.8+**
- **MySQL 8.0+**
- **IDE** (IntelliJ IDEA, Eclipse, VS Code)

### 1. Clonar o Repositório
```bash
git clone <url-do-repositorio>
cd api
```

### 2. Configurar o Banco de Dados MySQL

Crie o banco de dados:
```sql
CREATE DATABASE vollmed_api;
```

### 3. Configurar application.properties

O arquivo `src/main/resources/application.properties` já está configurado para desenvolvimento:

```properties
# Banco de Dados
spring.datasource.url=jdbc:mysql://localhost:3306/vollmed_api?allowPublicKeyRetrieval=true&useSSL=false
spring.datasource.username=root
spring.datasource.password=123456

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true

# Flyway
spring.flyway.baseline-on-migrate=true

# Servidor
server.port=8080

# JWT (desenvolvimento)
api.security.token.secret=12345678
```

### 4. Instalar Dependências
```bash
mvn clean install
```

---

## 🗄️ Banco de Dados

### Flyway - Versionamento de Banco

O projeto usa **Flyway** para gerenciar as migrações do banco de dados. Os scripts estão em `src/main/resources/db/migration/`.

#### Migrations Criadas:

| Versão | Arquivo | Descrição |
|--------|---------|-----------|
| V1 | `V1__create-table-medicos.sql` | Cria tabela de médicos |
| V2 | `V2__alter-table-medicos-add-column-telefone.sql` | Adiciona telefone |
| V3 | `V3__create-table-pacientes.sql` | Cria tabela de pacientes |
| V4 | `V4__alter-table-medicos-add-column-ativo.sql` | Adiciona status ativo |
| V5 | `V5__alter-table-pacientes-add-column-ativo.sql` | Adiciona status ativo |
| V6 | `V6__create-table-usuarios.sql` | Cria tabela de usuários |
| V7 | `V7__create-table-consultas.sql` | Cria tabela de consultas |
| V8 | `V8__alter-table-consultas-add-motivo-cancelamento.sql` | Adiciona motivo cancelamento |

### Modelo de Dados

```
┌─────────────────┐       ┌─────────────────┐
│    MEDICOS      │       │   PACIENTES     │
├─────────────────┤       ├─────────────────┤
│ id (PK)         │       │ id (PK)         │
│ nome            │       │ nome            │
│ email           │       │ email           │
│ crm             │       │ cpf             │
│ especialidade   │       │ telefone        │
│ telefone        │       │ logradouro      │
│ logradouro      │       │ bairro          │
│ bairro          │       │ cep             │
│ cep             │       │ complemento     │
│ complemento     │       │ numero          │
│ numero          │       │ uf              │
│ uf              │       │ cidade          │
│ cidade          │       │ ativo           │
│ ativo           │       └─────────────────┘
└─────────────────┘                │
         │                         │
         │         ┌───────────────┴──────────────┐
         │         │                              │
         └─────────┤        CONSULTAS             │
                   ├──────────────────────────────┤
                   │ id (PK)                      │
                   │ medico_id (FK)               │
                   │ paciente_id (FK)             │
                   │ data                         │
                   │ motivo_cancelamento          │
                   └──────────────────────────────┘

┌─────────────────┐
│   USUARIOS      │
├─────────────────┤
│ id (PK)         │
│ login           │
│ senha (BCrypt)  │
└─────────────────┘
```


---

## 🚀 Funcionalidades

### 1. Autenticação (JWT)

#### POST `/login`
Autentica um usuário e retorna um token JWT.

**Request:**
```json
{
  "login": "usuario@email.com",
  "senha": "123456"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

**Observações:**
- Token válido por 2 horas
- Senha criptografada com BCrypt
- Token deve ser enviado no header `Authorization: Bearer {token}` nas demais requisições

---

### 2. Médicos

#### POST `/medicos`
Cadastra um novo médico.

**Request:**
```json
{
  "nome": "Dr. João Silva",
  "email": "joao.silva@voll.med",
  "telefone": "11987654321",
  "crm": "123456",
  "especialidade": "CARDIOLOGIA",
  "endereco": {
    "logradouro": "Rua das Flores",
    "bairro": "Centro",
    "cep": "12345678",
    "cidade": "São Paulo",
    "uf": "SP",
    "numero": "100",
    "complemento": "Apto 101"
  }
}
```

**Especialidades disponíveis:**
- ORTOPEDIA
- CARDIOLOGIA
- GINECOLOGIA
- DERMATOLOGIA

#### GET `/medicos`
Lista todos os médicos ativos (paginado).

**Parâmetros:**
- `page` (opcional): número da página (padrão: 0)
- `size` (opcional): tamanho da página (padrão: 10)
- `sort` (opcional): campo de ordenação (ex: nome)

**Response:**
```json
{
  "content": [
    {
      "id": 1,
      "nome": "Dr. João Silva",
      "email": "joao.silva@voll.med",
      "crm": "123456",
      "especialidade": "CARDIOLOGIA"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10
  },
  "totalElements": 1
}
```

#### GET `/medicos/{id}`
Retorna detalhes de um médico específico.

#### PUT `/medicos`
Atualiza dados de um médico.

**Request:**
```json
{
  "id": 1,
  "nome": "Dr. João Silva Santos",
  "telefone": "11999887766",
  "endereco": {
    "logradouro": "Rua Nova",
    "bairro": "Jardins",
    "cep": "12345000",
    "cidade": "São Paulo",
    "uf": "SP",
    "numero": "200"
  }
}
```

#### DELETE `/medicos/{id}`
Inativa um médico (exclusão lógica).

---

### 3. Pacientes

#### POST `/pacientes`
Cadastra um novo paciente.

**Request:**
```json
{
  "nome": "Maria Santos",
  "email": "maria.santos@email.com",
  "telefone": "11987654321",
  "cpf": "12345678900",
  "endereco": {
    "logradouro": "Av. Paulista",
    "bairro": "Bela Vista",
    "cep": "01310100",
    "cidade": "São Paulo",
    "uf": "SP",
    "numero": "1000",
    "complemento": "Conj 101"
  }
}
```

#### GET `/pacientes`
Lista todos os pacientes ativos (paginado).

#### GET `/pacientes/{id}`
Retorna detalhes de um paciente específico.

#### PUT `/pacientes`
Atualiza dados de um paciente.

#### DELETE `/pacientes/{id}`
Inativa um paciente (exclusão lógica).

---

### 4. Consultas

#### POST `/consultas`
Agenda uma nova consulta.

**Request:**
```json
{
  "idMedico": 1,
  "idPaciente": 1,
  "data": "2024-12-20T10:00:00"
}
```

**Ou sem especificar médico (escolha automática):**
```json
{
  "idPaciente": 1,
  "data": "2024-12-20T10:00:00",
  "especialidade": "CARDIOLOGIA"
}
```

**Validações aplicadas:**
1. ✅ Horário de antecedência mínima (30 minutos)
2. ✅ Horário de funcionamento da clínica (7h às 19h, segunda a sábado)
3. ✅ Médico deve estar ativo
4. ✅ Paciente deve estar ativo
5. ✅ Médico não pode ter outra consulta no mesmo horário
6. ✅ Paciente não pode ter outra consulta no mesmo dia

**Response:**
```json
{
  "id": 1,
  "idMedico": 1,
  "idPaciente": 1,
  "data": "2024-12-20T10:00:00"
}
```

#### DELETE `/consultas`
Cancela uma consulta.

**Request:**
```json
{
  "idConsulta": 1,
  "motivo": "PACIENTE_DESISTIU"
}
```

**Motivos de cancelamento:**
- PACIENTE_DESISTIU
- MEDICO_CANCELOU
- OUTROS

**Validações:**
- Consulta deve ser cancelada com pelo menos 24 horas de antecedência

---

## 🔐 Segurança e Autenticação

### JWT (JSON Web Token)

O projeto utiliza autenticação stateless com JWT.

#### Fluxo de Autenticação:

```
1. Cliente → POST /login (usuário + senha)
2. API valida credenciais
3. API gera token JWT
4. API retorna token
5. Cliente armazena token
6. Cliente envia token em todas as requisições (Header: Authorization: Bearer {token})
7. API valida token em cada requisição
```

#### Configuração de Segurança

**SecurityConfigurations.java:**
- Desabilita CSRF (API stateless)
- Configura sessão como STATELESS
- Libera endpoints públicos: `/login`, `/swagger-ui/**`, `/v3/api-docs/**`
- Protege todos os outros endpoints

**SecurityFilter.java:**
- Intercepta todas as requisições
- Extrai e valida o token JWT
- Autentica o usuário no contexto do Spring Security

**TokenService.java:**
- Gera tokens JWT com expiração de 2 horas
- Valida tokens
- Extrai o subject (login do usuário) do token

#### Criptografia de Senhas

Senhas são criptografadas com **BCrypt** antes de serem salvas no banco:

```java
BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
String senhaCriptografada = encoder.encode("123456");
```

---

## 📚 Documentação da API (Swagger)

O projeto utiliza **SpringDoc OpenAPI** para gerar documentação interativa.

### Acessar o Swagger UI

Com a aplicação rodando, acesse:

```
http://localhost:8080/swagger-ui.html
```

ou

```
http://localhost:8080/swagger-ui/index.html
```

### Recursos do Swagger:

✅ **Visualização de todos os endpoints**
✅ **Modelos de request/response**
✅ **Testar requisições diretamente no navegador**
✅ **Autenticação JWT integrada**

### Como usar autenticação no Swagger:

1. Faça login no endpoint `/login`
2. Copie o token retornado
3. Clique no botão **"Authorize"** no topo da página
4. Cole o token no formato: `Bearer {seu-token}`
5. Clique em **"Authorize"**
6. Agora você pode testar os endpoints protegidos

### Configuração Personalizada

**SpringDocConfiguration.java:**
```java
@Bean
public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("Voll.med API")
            .version("v1")
            .description("API Rest da aplicação Voll.med")
        )
        .addSecurityItem(new SecurityRequirement().addList("bearer-key"))
        .components(new Components()
            .addSecuritySchemes("bearer-key",
                new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
            )
        );
}
```

### Documentação JSON

A especificação OpenAPI em JSON está disponível em:

```
http://localhost:8080/v3/api-docs
```

---

## 🧪 Testes Automatizados

O projeto possui testes automatizados para garantir a qualidade do código.

### Estrutura de Testes

```
src/test/java/med/voll/api/
├── controller/
│   ├── MedicoControllerTest.java      # Testes do controller de médicos
│   └── ConsultaControllerTest.java    # Testes do controller de consultas
└── ApiApplicationTests.java           # Teste de contexto
```

### Configuração de Testes

**application-test.properties:**
```properties
# Banco H2 em memória
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=false

# Desabilita Flyway nos testes
spring.flyway.enabled=false
```

### Exemplo de Teste - MedicoControllerTest

```java
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class MedicoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void deveriaCadastrarMedico() throws Exception {
        var json = """
            {
                "nome": "Dr. Teste",
                "email": "teste@email.com",
                "crm": "123456",
                "especialidade": "CARDIOLOGIA",
                "telefone": "11999999999",
                "endereco": {
                    "logradouro": "Rua Teste",
                    "bairro": "Bairro",
                    "cep": "12345678",
                    "cidade": "São Paulo",
                    "uf": "SP",
                    "numero": "100"
                }
            }
            """;

        mockMvc.perform(post("/medicos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }
}
```

### Executar Testes

```bash
# Executar todos os testes
mvn test

# Executar testes de uma classe específica
mvn test -Dtest=MedicoControllerTest

# Executar com relatório de cobertura
mvn test jacoco:report
```

### Tipos de Testes

1. **Testes de Unidade**: Testam classes isoladamente
2. **Testes de Integração**: Testam controllers com MockMvc
3. **Testes de Repository**: Testam acesso ao banco de dados
4. **Testes de Validação**: Testam regras de negócio


---

## 📦 Build e Deploy

### Profiles do Spring Boot

O projeto possui dois profiles de configuração:

#### 1. Profile de Desenvolvimento (padrão)
**Arquivo:** `application.properties`

```properties
# Banco de dados local
spring.datasource.url=jdbc:mysql://localhost:3306/vollmed_api
spring.datasource.username=root
spring.datasource.password=123456

# Logs detalhados
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# JWT com chave padrão
api.security.token.secret=12345678
```

#### 2. Profile de Produção
**Arquivo:** `application-prod.properties`

```properties
# Variáveis de ambiente (segurança)
spring.datasource.url=${DATASOURCE_URL}
spring.datasource.username=${DATASOURCE_USERNAME}
spring.datasource.password=${DATASOURCE_PASSWORD}

# Sem logs SQL
spring.jpa.show-sql=false

# JWT com variável de ambiente
api.security.token.secret=${JWT_SECRET:12345678}

# Não altera estrutura do banco
spring.jpa.hibernate.ddl-auto=none
```

### Gerar o Build (JAR)

#### 1. Compilar e gerar JAR

```bash
# Com testes
mvn clean package

# Sem testes (mais rápido)
mvn clean package -DskipTests
```

#### 2. Localização do JAR

O arquivo JAR será gerado em:
```
target/api-0.0.1-SNAPSHOT.jar
```

#### 3. Tamanho aproximado
- JAR completo: ~50-60 MB (inclui todas as dependências)

### Executar o JAR

#### Modo Desenvolvimento (usa application.properties)

```bash
java -jar target/api-0.0.1-SNAPSHOT.jar
```

#### Modo Produção (usa application-prod.properties)

**Linux/Mac:**
```bash
# Definir variáveis de ambiente
export DATASOURCE_URL=jdbc:mysql://servidor:3306/vollmed_api
export DATASOURCE_USERNAME=usuario_producao
export DATASOURCE_PASSWORD=senha_segura_producao
export JWT_SECRET=chave_jwt_super_secreta_producao

# Executar com profile prod
java -jar -Dspring.profiles.active=prod target/api-0.0.1-SNAPSHOT.jar
```

**Windows PowerShell:**
```powershell
# Definir variáveis de ambiente
$env:DATASOURCE_URL="jdbc:mysql://servidor:3306/vollmed_api"
$env:DATASOURCE_USERNAME="usuario_producao"
$env:DATASOURCE_PASSWORD="senha_segura_producao"
$env:JWT_SECRET="chave_jwt_super_secreta_producao"

# Executar com profile prod
java "-Dspring.profiles.active=prod" -jar target/api-0.0.1-SNAPSHOT.jar
```

**Windows Git Bash:**
```bash
# Definir variáveis de ambiente
export DATASOURCE_URL=jdbc:mysql://servidor:3306/vollmed_api
export DATASOURCE_USERNAME=usuario_producao
export DATASOURCE_PASSWORD=senha_segura_producao
export JWT_SECRET=chave_jwt_super_secreta_producao

# Executar com profile prod
java -jar -Dspring.profiles.active=prod target/api-0.0.1-SNAPSHOT.jar
```

### Deploy em Servidor

#### Pré-requisitos no Servidor:
1. ✅ Java 17 instalado
2. ✅ MySQL configurado e rodando
3. ✅ Banco de dados criado
4. ✅ Variáveis de ambiente configuradas

#### Passos para Deploy:

**1. Transferir o JAR para o servidor**
```bash
scp target/api-0.0.1-SNAPSHOT.jar usuario@servidor:/opt/vollmed/
```

**2. Configurar variáveis de ambiente no servidor**

Edite o arquivo `/etc/environment` ou crie um script:

```bash
# /opt/vollmed/env.sh
export DATASOURCE_URL=jdbc:mysql://localhost:3306/vollmed_api
export DATASOURCE_USERNAME=vollmed_user
export DATASOURCE_PASSWORD=senha_super_segura
export JWT_SECRET=chave_jwt_producao_muito_segura_123456
```

**3. Criar serviço systemd (Linux)**

Crie o arquivo `/etc/systemd/system/vollmed.service`:

```ini
[Unit]
Description=Voll.med API
After=mysql.service

[Service]
Type=simple
User=vollmed
WorkingDirectory=/opt/vollmed
EnvironmentFile=/opt/vollmed/env.sh
ExecStart=/usr/bin/java -jar -Dspring.profiles.active=prod /opt/vollmed/api-0.0.1-SNAPSHOT.jar
Restart=always
RestartSec=10

[Install]
WantedBy=multi-user.target
```

**4. Iniciar o serviço**
```bash
sudo systemctl daemon-reload
sudo systemctl enable vollmed
sudo systemctl start vollmed
sudo systemctl status vollmed
```

**5. Verificar logs**
```bash
sudo journalctl -u vollmed -f
```

### Deploy com Docker (Opcional)

**Dockerfile:**
```dockerfile
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/api-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "app.jar"]
```

**docker-compose.yml:**
```yaml
version: '3.8'

services:
  mysql:
    image: mysql:8.0
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: vollmed_api
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql

  api:
    build: .
    ports:
      - "8080:8080"
    environment:
      DATASOURCE_URL: jdbc:mysql://mysql:3306/vollmed_api
      DATASOURCE_USERNAME: root
      DATASOURCE_PASSWORD: root
      JWT_SECRET: chave_secreta_docker
    depends_on:
      - mysql

volumes:
  mysql_data:
```

**Executar com Docker:**
```bash
# Build da imagem
docker build -t vollmed-api .

# Executar com docker-compose
docker-compose up -d
```

### Segurança em Produção

#### ⚠️ Checklist de Segurança:

- [ ] Usar HTTPS (SSL/TLS)
- [ ] Configurar CORS adequadamente
- [ ] Usar senhas fortes para banco de dados
- [ ] Gerar chave JWT complexa (mínimo 256 bits)
- [ ] Não commitar arquivos `.jar` no Git
- [ ] Não expor senhas em logs
- [ ] Configurar firewall no servidor
- [ ] Fazer backup regular do banco de dados
- [ ] Monitorar logs de acesso
- [ ] Atualizar dependências regularmente

#### Gerar Chave JWT Segura:

```bash
# Linux/Mac
openssl rand -base64 64

# Ou use um gerador online confiável
```

### Monitoramento

#### Endpoints de Monitoramento (Spring Actuator - opcional)

Adicione no `pom.xml`:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

Endpoints disponíveis:
- `/actuator/health` - Status da aplicação
- `/actuator/info` - Informações da aplicação
- `/actuator/metrics` - Métricas de performance

---

## 🚀 Como Executar

### Passo a Passo Completo

#### 1. Clonar o Repositório
```bash
git clone <url-do-repositorio>
cd api
```

#### 2. Configurar o Banco de Dados
```sql
CREATE DATABASE vollmed_api;
```

#### 3. Configurar application.properties

Edite `src/main/resources/application.properties` com suas credenciais:
```properties
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

#### 4. Executar a Aplicação

**Opção 1: Via Maven**
```bash
mvn spring-boot:run
```

**Opção 2: Via IDE**
- Abra o projeto na IDE
- Execute a classe `ApiApplication.java`

**Opção 3: Via JAR**
```bash
mvn clean package -DskipTests
java -jar target/api-0.0.1-SNAPSHOT.jar
```

#### 5. Verificar se está rodando

Acesse no navegador:
```
http://localhost:8080/swagger-ui.html
```

#### 6. Criar um Usuário de Teste

Execute no MySQL:
```sql
INSERT INTO usuarios (login, senha) 
VALUES ('admin@voll.med', '$2a$10$Y50UaMFOxteibQEYLrwuHeehHYfcoafCopUazP12.rqB41bsolF5.');
-- Senha: 123456
```

#### 7. Fazer Login

**POST** `http://localhost:8080/login`
```json
{
  "login": "admin@voll.med",
  "senha": "123456"
}
```

#### 8. Usar o Token

Copie o token retornado e use no header das próximas requisições:
```
Authorization: Bearer {seu-token-aqui}
```

---

## 📝 Comandos Úteis

### Maven

```bash
# Limpar build anterior
mvn clean

# Compilar
mvn compile

# Executar testes
mvn test

# Gerar JAR
mvn package

# Pular testes
mvn package -DskipTests

# Instalar no repositório local
mvn install

# Ver dependências
mvn dependency:tree

# Atualizar dependências
mvn versions:display-dependency-updates
```

### Git

```bash
# Ver status
git status

# Adicionar arquivos
git add .

# Commit
git commit -m "Mensagem do commit"

# Push
git push origin main

# Pull
git pull origin main

# Ver branches
git branch

# Criar branch
git checkout -b nova-branch

# Mudar de branch
git checkout main
```

### MySQL

```bash
# Conectar ao MySQL
mysql -u root -p

# Listar bancos
SHOW DATABASES;

# Usar banco
USE vollmed_api;

# Listar tabelas
SHOW TABLES;

# Ver estrutura de tabela
DESCRIBE medicos;

# Ver dados
SELECT * FROM medicos;
```

---

## 🐛 Troubleshooting

### Problema: Erro de conexão com MySQL

**Erro:**
```
Communications link failure
```

**Solução:**
1. Verifique se o MySQL está rodando
2. Confirme usuário e senha no `application.properties`
3. Verifique se o banco `vollmed_api` existe

### Problema: Flyway não executa migrations

**Erro:**
```
Flyway failed to initialize
```

**Solução:**
```properties
# Adicione no application.properties
spring.flyway.baseline-on-migrate=true
spring.flyway.validate-on-migrate=false
```

### Problema: Erro 401 Unauthorized

**Solução:**
1. Faça login e obtenha um novo token
2. Verifique se o token está no formato: `Bearer {token}`
3. Confirme se o token não expirou (válido por 2 horas)

### Problema: Porta 8080 já está em uso

**Solução:**
```properties
# Mude a porta no application.properties
server.port=8081
```

Ou mate o processo:
```bash
# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# Linux/Mac
lsof -i :8080
kill -9 <PID>
```

---

## 📚 Recursos Adicionais

### Documentação Oficial

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Spring Security](https://spring.io/projects/spring-security)
- [Flyway](https://flywaydb.org/documentation/)
- [SpringDoc OpenAPI](https://springdoc.org/)

### Cursos Relacionados

- Alura - Spring Boot 3
- Alura - Spring Security
- Alura - Testes com Spring Boot

---

## 👨‍💻 Autor

**Guilherme Falcão**
- Projeto desenvolvido durante o curso de Spring Boot da Alura
- Dataprev

---

## 📄 Licença

Este projeto foi desenvolvido para fins educacionais.

---

## 🎯 Próximos Passos

- [ ] Implementar paginação em todas as listagens
- [ ] Adicionar filtros de busca
- [ ] Implementar relatórios
- [ ] Adicionar envio de e-mails
- [ ] Implementar notificações
- [ ] Adicionar dashboard administrativo
- [ ] Implementar auditoria de ações
- [ ] Adicionar mais testes automatizados
- [ ] Implementar cache com Redis
- [ ] Adicionar monitoramento com Prometheus/Grafana

---

**Última atualização:** Dezembro 2024

