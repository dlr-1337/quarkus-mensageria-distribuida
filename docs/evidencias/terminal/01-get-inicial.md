# GET inicial /mensagens

Metodo: GET
URL: http://localhost:8080/mensagens

Payload enviado:
```json
{}
```

Resposta bruta:
```http
HTTP/1.1 200
content-length: 2
connection: close
Content-Type: application/json;charset=UTF-8

[]
```

Interpretacao teorica:
- Send: O cliente solicita ao servidor o estado atual da lista em memoria via HTTP GET.
- Receive: O servidor devolve 200 OK com uma lista vazia, confirmando que nao havia mensagens cadastradas.
