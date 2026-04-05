# POST /mensagens

Metodo: POST
URL: http://localhost:8080/mensagens

Payload enviado:
```json
{
  "remetente": "alice",
  "conteudo": "Ola do POST"
}
```

Resposta bruta:
```http
HTTP/1.1 201
Content-Type: application/json;charset=UTF-8
content-length: 95
connection: close
Location: http://localhost:8080/mensagens/1

{"id":1,"remetente":"alice","conteudo":"Ola do POST","timestamp":"2026-04-05T19:08:19.4726541"}
```

Interpretacao teorica:
- Send: O cliente envia os dados essenciais da mensagem; o servidor completa id e timestamp.
- Receive: O servidor devolve 201 Created com o recurso salvo e o cabecalho Location apontando para /mensagens/1.
