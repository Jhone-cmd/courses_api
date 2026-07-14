# Courses API

API REST desenvolvida em Java (Spring Boot) para gerenciar cursos, categorias e usuários. Projeto construído seguindo as aulas da plataforma Rocketseat como material de estudo.

**Descrição**

- Projeto de exemplo que implementa operações CRUD para cursos e categorias, autenticação com JWT e regras de autorização para usuários e professores.
- Inclui tratamento de exceções centralizado, validações e mapeadores (DTO -> entidade).

**Funcionalidades**

- Cadastro, listagem, atualização e remoção de categorias.
- Cadastro, listagem, atualização e remoção de cursos (associação com categorias).
- Gestão de usuários: registro, autenticação e papéis/autorizações (ex.: professor, usuário comum).
- Autenticação via JWT com chaves RSA (configuração em `security`).
- Redirecionamento de rotas públicas (controller de redirect) e documentação Swagger configurada.

**Tecnologias**

- Java 17+
- Spring Boot
- Spring Security
- JWT (RSA)
- Maven
- Docker / Docker Compose (opcional)

**Requisitos**

- JDK 17 ou superior
- Maven 3.6+
- Docker & Docker Compose (opcional)

**Como executar (local)**

1. Build e execução com Maven:

```bash
mvn clean package
mvn spring-boot:run
```

2. A API estará disponível por padrão em `http://localhost:8080` (ver `application.properties`).

**Como executar com Docker**

1. Build da imagem e iniciar via Docker Compose:

```bash
docker-compose up --build
```

**Executar testes**

```bash
mvn test
```

**Estrutura de pastas**

- `src/main/java/br/com/jhonecmd/courses_api/`
  - `CoursesApiApplication.java` : classe principal do Spring Boot
  - `config/` : configurações da aplicação
  - `controllers/` : controllers REST (ex.: `RedirectController`)
  - `exceptions/` : definições de exceções customizadas e handler
  - `modules/`
    - `categories/` : pacote com entidade, serviço, repositório e controller de categorias
    - `users/` : pacote com gestão de usuários
  - `providers/` : providers como `JwtProvider.java`
  - `security/` : configuração de segurança (`RSAKeyConfig.java`, `SecurityConfig.java`, `SecurityFilter.java`)
  - `utils/` : mapeadores e utilitários (`CategoryMapper.java`, `CourseMapper.java`, `UserMapper.java`)
- `src/main/resources/` : arquivos de configuração (`application.properties`, `application-test.properties`, `META-INF`)
- `pom.xml` : arquivo de build do Maven
- `Dockerfile` e `docker-compose.yml` : arquivos para conteinerização

(Observação: a árvore completa do projeto está disponível no repositório.)

**Principais arquivos e responsabilidades**

- `CoursesApiApplication.java` : inicializa a aplicação Spring Boot.
- `SecurityConfig.java` : configura filtros, rotas públicas/privadas e provedores de autenticação.
- `JwtProvider.java` : lógica de criação/validação de tokens JWT.
- `ExceptionHandlerController.java` : centraliza respostas de erro.
- `SwaggerConfig.java` : configuração da documentação Swagger / OpenAPI.

**Ambiente / Variáveis**

- Caso a aplicação utilize variáveis para chaves RSA ou URLs externas, configure no `application.properties` ou via variáveis de ambiente.

**Observações**

- Este projeto foi desenvolvido como exercício e aprendizado, acompanhando as aulas da plataforma Rocketseat.
- Uso educacional: adaptações e melhorias podem ser aplicadas conforme necessidade.

**Contribuição**

- Sinta-se à vontade para abrir issues e pull requests.

**Licença**

- MIT

--

Feito assistindo às aulas da plataforma Rocketseat.
