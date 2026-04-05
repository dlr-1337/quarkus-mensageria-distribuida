# GET /mensagens/1 apos DELETE

Metodo: GET
URL: http://localhost:8080/mensagens/1

Payload enviado:
```json
{}
```

Resposta bruta:
```http
HTTP/1.1 404
connection: close
content-length: 0
```

Interpretacao teorica:
- Send: O cliente tenta receber novamente uma mensagem ja removida.
- Receive: O servidor devolve 404 Not Found, mostrando que o recurso nao esta mais disponivel.
