# GET /mensagens/999

Metodo: GET
URL: http://localhost:8080/mensagens/999

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
- Send: O cliente tenta receber um recurso que nao existe no armazenamento em memoria.
- Receive: O servidor devolve 404 Not Found, sinalizando ausencia do identificador solicitado.
