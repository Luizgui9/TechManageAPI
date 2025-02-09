# TechManageAPI
### API para gerenciamento de usuários da TechManage

## Visão geral:
TechManage API é uma aplicação RESTful desenvolvida em Spring Boot com Maven (embutido) para gerenciar usuários. Ela oferece operações básicas de CRUD (Criar, Ler, Atualizar, Excluir) e utiliza Spring Data JPA para persistência em um banco de dados H2. Validações de dados são realizadas via Bean Validation. O projeto inclui testes unitários e de integração para garantir o funcionamento da aplicação.

### Requisitos do sistema:
- Java JDK 17 (ou superior)
- Lombok

## Passos para build e execução local:
1. Clone o repositório: https://github.com/Luizgui9/TechManageAPI.git
2. Importe o projeto em sua IDE por Import Existing Maven Project.
3. Faça download do <a href="https://projectlombok.org/download" target="_blank">Lombok</a>  
3.1 Após download do JAR, dê um clique duplo para abri-lo e clique em Install após carregar.  
3.2 Após instalação bem sucedida do Lombok, reinicie sua IDE.
4. Execute um Maven Update Project.
5. Na raiz do projeto, execute um Maven Build, e, para realizar build do projeto, utilize o Goals: `clean install`
6. Para executar o projeto localmente, vá para `\src\main\java` e acesse o pacote `com.techgroup.techmanage`  
   e execute um Run Java Application na classe `TechmanageApplication.java`

> [!NOTE]
> Em casos de problemas para executar a subida da API localmente após seguir os passos, recomenda-se executar um Clean no projeto e seguir os passos novamente.
  
---
  
### Sobre Banco de Dados:
O projeto utiliza banco de dados H2 embutido. Inclui arquivos de script SQL `data.sql` e `schema.sql` em `src/main/resources` responsaveis por criar tabela e popular dados iniciais no momento de execução de projeto localmente. 

---

# Endpoints e exemplos de requisições

## Serviço: Criar um Novo Usuário  
Endpoint:  **POST** `/api/users`   
Descrição: Cria um novo usuário.   
Exemplo de Requisição cURL:  
```
curl --location 'localhost:8080/api/users' \
--header 'Content-Type: application/json' \
--data-raw '{
    "fullName": "Roberto",
    "email": "rob_valdo@email2sample.com",
    "phone": "+55 12 4977-2108",
    "birthDate": "1990-05-15T03:00:00.000+00:00",
    "userType": "ADMIN"
}'
```

### Exemplo de Resposta bem sucedida (201 Created): 
```
{
    "userId": 36,
    "fullName": "Roberto",
    "email": "rob_valdo@email2sample.com",
    "phone": "+55 12 4977-2108",
    "birthDate": "1990-05-15T03:00:00.000+00:00",
    "userType": "ADMIN"
}
```

## Serviço: Buscar Todos os Usuários  
Endpoint: **GET** `/api/users`  
Descrição: Retorna uma lista de todos os usuários.  
Exemplo de Requisição cURL:  
`curl --location 'localhost:8080/api/users'`

Exemplo de Resposta (200 OK):  
```
[
    {
        "userId": 1,
        "fullName": "Alice Johnson",
        "email": "alice@example.com",
        "phone": "+55 12 34977-2108",
        "birthDate": "1990-05-15T03:00:00.000+00:00",
        "userType": "ADMIN"
    },
    {
        "userId": 2,
        "fullName": "Bob Smith",
        "email": "bob@example.com",
        "phone": "+55 11 36791-5784",
        "birthDate": "1985-10-22T03:00:00.000+00:00",
        "userType": "VIEWER"
    },
    {
        "userId": 3,
        "fullName": "Ruan",
        "email": "ru_an@example.com",
        "phone": "+55 19 34329-2754",
        "birthDate": "2000-11-05T02:00:00.000+00:00",
        "userType": "EDITOR"
    }
]
```
**Caso nenhum usuário seja encontrado, a API retornará HttpStatus 404 Not Found, com mensagem "Nenhum usuário encontrado."**

## Serviço: Buscar um Usuário por ID  
Endpoint: **GET** `/api/users/{id}`   
Descrição: Retorna os dados do usuário com o ID especificado.  
Exemplo de Requisição cURL: `curl --location 'localhost:8080/api/users/1'`  

Exemplo de Resposta (200 OK):  
```
{
    "userId": 1,
    "fullName": "Alice Johnson",
    "email": "alice@example.com",
    "phone": "+55 12 34977-2108",
    "birthDate": "1990-05-15T03:00:00.000+00:00",
    "userType": "ADMIN"
}
```
**Caso nenhum usuário seja encontrado, a API retornará HttpStatus 404 Not Found, com mensagem "Usuário com ID ' ' não encontrado "**

## Serviço: Buscar um Usuário por E-mail  
Endpoint: **GET** `/api/users/email/{email}`  
Descrição: Retorna os dados do usuário com o e-mail especificado.  
Exemplo de Requisição cURL: `curl --location 'localhost:8080/api/users/email/alice@example.com'`  

Exemplo de Resposta (200 OK):  
```
{
    "userId": 1,
    "fullName": "Alice Johnson",
    "email": "alice@example.com",
    "phone": "+55 12 34977-2108",
    "birthDate": "1990-05-15T03:00:00.000+00:00",
    "userType": "ADMIN"
}
```
**Caso nenhum email seja encontrado, a API retornará HttpStatus 404 Not Found, com mensagem "Usuário com e-mail 'alice@example.com' não encontrado"**

## Serviço: Atualizar um Usuário  
Endpoint: **PUT** ``/api/users/{id}``  
Descrição: Atualiza os dados do usuário com o ID especificado.   
Exemplo de Requisição cURL: 
```
curl --location --request PUT 'localhost:8080/api/users/1' \
--header 'Content-Type: application/json' \
--data-raw '{
    "fullName": "Alice Stwart",
    "email": "alice@example.com",
    "phone": "+55 12 34977-2108",
    "birthDate": "1990-05-15T03:00:00.000+00:00",
    "userType": "ADMIN"
}'
```

Exemplo de Resposta (200 OK):  
```
{
    "userId": 1,
    "fullName": "Alice Stwart",
    "email": "alice@example.com",
    "phone": "+55 12 34977-2108",
    "birthDate": "1990-05-15T03:00:00.000+00:00",
    "userType": "ADMIN"
}
```

**Caso nenhum usuario seja encontrado, a API retornará HttpStatus 404 Not Found, com mensagem "Usuário com ID ' ' não encontrado"**


## Serviço: Excluir um Usuário  
Endpoint: **DELETE** ``/api/users/{id}``   
Descrição: Exclui o usuário com o ID especificado.   
Exemplo de Requisição cURL: ``curl --location --request DELETE 'localhost:8080/api/users/2'``  

**Caso nenhum usuario seja encontrado, a API retornará HttpStatus 404 Not Found, com mensagem "Usuário com ID ' ' não encontrado"**  

---  

# TESTES UNITARIOS E INTEGRADOS
O projeto possui testes unitários para os serviços e testes de integração para os endpoints.  
As classes de testes ficam no caminho ``src/test/java``, dentro dos respectivos pacotes:  
``com.techgroup.techmanage``   
``com.techgroup.techmanage.controller``  
``com.techgroup.techmanage.service``  

#### Como executar os testes:
1. Dentro do pacote ``com.techgroup.techmanage``, localizar classe ``TechmanageApplicationTests.java``
   e executa um **Run JUnit Test**.

> [!NOTE]
> Executar **Run JUnit Test** na classe ``TechmanageApplicationTests.java`` fará com que seja executado e testado o carregamento de contexto do Springboot, e sequencialmente, todas as classes de testes serão executadas também. Caso queira executar individualmente as classes ``UserControllerTest.java`` ou ``UserServiceTest.java``, basta seguir os passos descritos acima, porém executando a classe desejada presente dentro de outro pacote do caminho ``src/test/java``.

---   

# Considerações Finais
### Validações de campos de entrada:  
- **fullName:** Campo de preenchimento obrigatório, máximo de 255 caracteres.    
- **email:** Deve estar em formato válido (ex.: fulano@email), campo tem preenchimento obrigatório e unico (não é possível criar um usuário com um email já cadastrado no banco de dados).  
- **phone:** Deve seguir o formato internacional: +XX XX XXXXX-XXXX.  
- **birthDate:** Deve ser uma data no passado e data deve seguir padrão localDate (yyyy-MM-dd).  
- **userType:** Enum com valores possíveis: ADMIN, EDITOR, VIEWER.  
