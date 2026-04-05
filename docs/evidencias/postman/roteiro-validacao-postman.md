# Roteiro de Validacao no Postman

Este roteiro usa os itens ja exportados em:

- `docs/postman/quarkus-mensagens.postman_collection.json`
- `docs/postman/localhost-8080.postman_environment.json`

## Ambiente

- Variavel `baseUrl`: `http://localhost:8080`

## Sequencia sugerida

| Ordem | Item da collection | Objetivo | Status esperado |
| --- | --- | --- | --- |
| 1 | `01 - GET inicial /mensagens` | Confirmar lista vazia no inicio | `200 OK` |
| 2 | `02 - POST /mensagens` | Criar uma mensagem | `201 Created` |
| 3 | `03 - GET /mensagens/1` | Recuperar a mensagem criada | `200 OK` |
| 4 | `04 - GET /mensagens/999` | Demonstrar busca invalida | `404 Not Found` |
| 5 | `05 - DELETE /mensagens/1` | Remover a mensagem criada | `200 OK` |
| 6 | `06 - GET /mensagens/1 apos DELETE` | Provar que a remocao ocorreu | `404 Not Found` |

## O que observar em cada tela

### GET inicial

- Body: `[]`
- Interpretacao: nao ha mensagens armazenadas ainda.

### POST

- Body enviado:

```json
{
  "remetente": "alice",
  "conteudo": "Ola do POST"
}
```

- Status: `201`
- Deve existir cabecalho `Location`
- O corpo deve voltar com `id` e `timestamp`

### GET por id

- Status: `200`
- Deve retornar exatamente a mensagem criada antes

### GET inexistente

- Status: `404`
- Serve para justificar o tratamento de recurso ausente

### DELETE por id

- Status: `200`
- O corpo deve devolver a mensagem removida

### GET apos DELETE

- Status: `404`
- Confirma que o recurso nao existe mais depois da exclusao

## Interpretacao teorica pronta para a defesa

- `POST` representa o `send` mais evidente: o cliente envia dados para o servidor.
- `GET` representa o pedido de `receive`: o cliente pede o estado e recebe a representacao.
- `DELETE` representa uma instrucao de alteracao de estado seguida do `receive` da confirmacao.
