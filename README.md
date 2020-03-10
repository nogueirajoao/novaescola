# Cadastro de Clientes

 Para rodar a aplicação localmente via linha de comando: ```mvn spring-boot:run ```

 URL raiz: http://localhost:8080/cliente

Banco de dados utilizado: H2 DB (em memória).

Testes unitários simples.
## Endpoints:
### Cadastro do cliente
\[`POST`\] 
`/cliente`

Exemplo de corpo:
```json
{
    "nome": "José",
    "email": "jose@email.com",
    "dataNascimento": "01/01/2000"
}
```

Resposta: 

    HTTP Status: 201
```json
{
    "id": 1,
    "nome": "José",
    "email": "jose@email.com",
    "dataNascimento": "01/01/2000"
}
```

### Listagem do cliente
\[`GET`\] `/cliente?limite={numero}&pagina={numero}`

    Parametros Opcionais:
        - limite: valor que define quantidade máxima a ser retornada, caso não seja informado, valor padrão: 10
        - pagina: paginação da listagem baseado no total,
            caso não informado por padrão: 0

Corpo: _vazio_

Resposta: lista com objetos cadastrados na base limitados pelo paramêtro `limite` acrescido com o total de dados existente na base

Exemplo de resposta de requisição com `limite` igual a 1

    HTTP Status: 200
```json
{
    "total": 2039,
    "lista": [
        {
            "id": 1,
            "nome": "José",
            "email": "jose@email.com",
            "dataNascimento": "01/01/2000"
        }
    ]
}
```

### Detalhamento do cliente
\[`GET`\] `/cliente/{id}`

    Parametro Obrigatório:
        - id: identificador do cliente a ser retornado

Corpo: _vazio_

Resposta: objeto com dados do cliente

    HTTP Status: 200
```json
{
    "id": 1,
    "nome": "José",
    "email": "jose@email.com",
    "dataDeNascimento": "01/01/2000"
}
```

### Alteração do cliente
\[`PUT`\] `/cliente/{id}`

    Parametro obrigatório:
    - id: identificador do cliente a ser atualizado

Corpo: dados a serem atualizados do cliente

```json
{
    "nome": "José da Silva"
}
```

Resposta: objeto com dados do cliente

    HTTP Status: 200
```json
{
    "id": 1,
    "nome": "José da Silva",
    "email": "jose@email.com",
    "dataDeNascimento": "01/01/2000"
}
```

### Remoção do cliente
\[`DELETE`\] `/cliente/{id}`

    Parametro obrigatório:
        - id: identificador do cliente a ser removido

Corpo: _vazio_

Resposta: _vazio_

    HTTP Status: 200
