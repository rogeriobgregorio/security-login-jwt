# Security Login JWT
[![Status](https://img.shields.io/badge/Status-Concluído-brightgreen.svg)](https://github.com/seu-usuario/seu-projeto)

O **Security Login JWT** é uma API REST robusta desenvolvida para fornecer autenticação e autorização em aplicações que exigem controle de acesso. A aplicação permite definir diferentes níveis de acesso, onde os usuários podem acessar rotas específicas dependendo de suas permissões. Construído em Java e no Ecossistema Spring, o sistema prioriza código limpo e a implementação dos princípios SOLID, criando um software flexível, escalável e de fácil manutenção. As tecnologias como Spring Security, JWT, JUnit, Mockito e Log4j2 são utilizadas para garantir a qualidade, segurança e eficiência do sistema.

## Demonstração
#### Assista ao vídeo demonstrativo:

![-gif](https://github.com/rogeriobgregorio/.gif)


## Problema a ser resolvido:
Em muitas aplicações, a gestão de autenticação e autorização de usuários é complexa e essencial para a segurança do sistema. Este projeto visa resolver esse problema ao fornecer uma solução simples e segura para autenticação via JWT, além de permitir o controle granular de acesso com base nas roles dos usuários. O sistema também oferece a flexibilidade para que administradores possam alterar dinamicamente as permissões dos usuários.


## Principais funcionalidades:
- Registro de usuários: Permite o cadastro de novos usuários no sistema.
- Login: Realiza a autenticação do usuário por meio de um processo seguro, utilizando JWT.
- Controle de acesso baseado em roles: O sistema permite definir diferentes níveis de acesso, onde os usuários podem acessar rotas específicas dependendo de suas permissões e roles.
- Gestão de roles por administradores: Administradores conseguem alterar as roles dos usuários, podendo promover um usuário comum a administrador ou reverter a alteração para um status de usuário comum a qualquer momento.
- Segurança: A autenticação é realizada com tokens JWT, garantindo que cada requisição seja validada de forma segura.


## Modelo Conceitual
**Diagrama Entidade-Relacionamento**

![der](https://github.com/rogeriobgregorio/security-login-jwt/blob/main/documentation/der.png)

## Stack utilizada
**Back-end:**
- Java 17
- Spring Boot
- Spring Data JPA
- Hibernate
- Spring Security
- JWT
- Junit
- Mockito
- Docker

**Front-end:**
- HTML5
- CSS3
- Javascript

**Banco de Dados:**
- PostgreSQL


## Documentação
[Documentação da API](http://localhost:8080/api/v1/swagger-ui/index.html#):
a documentação completa da API pode ser acessada localmente ao executar a aplicação e visitar http://localhost:8080/api/v1/swagger-ui/index.html#.
Esta interface interativa, gerada pelo Swagger, fornece detalhes sobre cada endpoint, parâmetros de solicitação, respostas esperadas e exemplos práticos de uso.


## Rodando localmente
Siga as etapas abaixo para configurar e executar o projeto Java com Spring localmente:

1. Certifique-se de ter o Java 17 JDK instalado. Caso não tenha, faça o download e a instalação a partir do site oficial da [Oracle](https://oracle.com/).

2. Clone o repositório do projeto:
```bash
  git clone https://github.com/rogeriobgregorio/security-login-jwt
```

3. Acesse o diretório do projeto:
```bash
  cd security-login-jwt
```

4. Execute o projeto:
```bash
  ./mvnw spring-boot:run
```
Caso prefira, abra o projeto em uma IDE, como IntelliJ por exemplo, e execute o projeto.

Após concluir essas etapas, o seu servidor Spring estará em execução localmente na porta 8080.

A interface para consumir a API esta localizada na pasta "front-end".

5. Abra a página index.html no browser utilizando um servidor, como live server por exemplo.

6. Importante: você deve atribuir valores nas variáveis de ambiente do projeto **JWT_SECRET** e **PASSWORD_SECRET**.

7. Para logar como administrador utilize o usuário **admin@email.com**, a senha será o valor que você atribui à variável **PASSWORD_SECRET** no passo anterior.


## Referência
- [Spring Framework](https://spring.io/):
  consulte a documentação oficial do Spring Framework para obter informações detalhadas sobre o framework utilizado no backend.
  Esta referência abrange conceitos fundamentais, configurações avançadas e as melhores práticas recomendadas pela comunidade Spring.

- [Swagger Documentation](https://swagger.io/):
  o Swagger é integrado à API para facilitar a compreensão e interação. Obtenha mais informações sobre o Swagger em https://swagger.io/.


## Autor
#### Rogério Bernardo Gregório
- Linkedin: [linkedin.com/in/rogeriogregorio](https://linkedin.com/in/rogeriogregorio)
- Email: [bernardo.rogerio93@gmail.com](mailto:bernardo.rogerio93@gmail.com)