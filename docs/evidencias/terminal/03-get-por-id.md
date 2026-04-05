# GET /mensagens/1

Metodo: GET
URL: http://localhost:8080/mensagens/1

Payload enviado:
```json
{}
```

Resposta bruta:
```http
HTTP/1.1 200
Content-Type: application/json;charset=UTF-8
content-length: 95
connection: close

{"id":1,"remetente":"alice","conteudo":"Ola do POST","timestamp":"2026-04-05T19:08:19.4726541"}
```

Interpretacao teorica:
- Send: O cliente pede a representacao atual da mensagem de id 1.
- Receive: O servidor devolve 200 OK com o JSON completo da mensagem criada anteriormente.
