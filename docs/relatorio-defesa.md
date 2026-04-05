# Relatorio Expandido de Defesa

## 1. Tema

Desenvolvimento de uma API REST em Quarkus para representar um sistema simples de mensageria distribuida via HTTP.

## 2. Problema que o trabalho resolve

O trabalho precisava mostrar, de forma objetiva, como dois processos podem trocar mensagens usando o protocolo HTTP. Em vez de introduzir complexidade com banco de dados, fila, autenticacao ou processamento assincrono, a solucao foi reduzida ao essencial:

- um cliente envia requisicoes HTTP;
- um servidor REST recebe e processa essas requisicoes;
- o servidor responde com JSON e status codes coerentes;
- toda a troca pode ser interpretada teoricamente como `send` e `receive`.

## 3. Solucao implementada

A aplicacao foi criada com Quarkus `3.34.2`, Maven Wrapper e extensao `quarkus-rest-jackson`. O projeto possui tres pecas principais:

1. `Mensagem`: classe de dominio com `id`, `remetente`, `conteudo` e `timestamp`.
2. `MensagemService`: camada de regra de negocio e armazenamento em memoria.
3. `MensagemResource`: camada HTTP com os endpoints da API.

## 4. Explicacao arquitetural

### 4.1 Camada REST

O `MensagemResource` usa JAX-RS (`jakarta.ws.rs`) para expor:

- `GET /mensagens`
- `GET /mensagens/{id}`
- `POST /mensagens`
- `DELETE /mensagens/{id}`

Mesmo usando as anotacoes classicas de JAX-RS, o runtime recomendado no Quarkus atual e o Quarkus REST. Por isso a extensao escolhida foi `quarkus-rest-jackson`, e nao RESTEasy Classic.

### 4.2 Camada de negocio

O `MensagemService` esta anotado com `@ApplicationScoped` e centraliza:

- listagem;
- busca por id;
- criacao de mensagens;
- remocao de mensagens;
- limpeza do estado para testes.

### 4.3 Armazenamento em memoria

Foram usadas as estruturas:

- `CopyOnWriteArrayList<Mensagem>` para armazenar mensagens;
- `AtomicLong` para gerar ids sequenciais.

Essa combinacao atende bem o escopo pequeno e didatico do trabalho.

## 5. Fluxo teorico do `POST /mensagens`

1. O cliente faz um `send` de um JSON com `remetente` e `conteudo`.
2. O servidor recebe a mensagem HTTP.
3. O `MensagemService` cria a representacao oficial do recurso, gerando `id` e `timestamp`.
4. A mensagem passa a existir no armazenamento em memoria.
5. O servidor responde ao cliente com `201 Created`, corpo JSON e cabecalho `Location`.
6. O cliente faz um `receive` da confirmacao da criacao.

Em linguagem de redes e sistemas distribuido, o POST representa um envio de estado do cliente para o servidor, seguido de uma resposta formal do servidor.

## 6. Endpoints e explicacao funcional

| Metodo | Endpoint | Papel no sistema | Status principal |
| --- | --- | --- | --- |
| `GET` | `/mensagens` | Retorna o estado atual da lista | `200 OK` |
| `GET` | `/mensagens/{id}` | Recupera uma mensagem especifica | `200 OK` ou `404` |
| `POST` | `/mensagens` | Cria uma nova mensagem | `201 Created` |
| `DELETE` | `/mensagens/{id}` | Remove uma mensagem existente | `200 OK` ou `404` |

## 7. Justificativa dos status codes

### `200 OK`

Foi usado quando a operacao foi concluida com sucesso e havia uma resposta valida para devolver, como no `GET` bem-sucedido e no `DELETE` de um recurso existente.

### `201 Created`

Foi usado no `POST` porque um novo recurso passou a existir no servidor. Alem disso, o cabecalho `Location` aponta para o URI do recurso criado.

### `404 Not Found`

Foi usado quando o cliente pediu um identificador que nao existe mais ou nunca existiu na lista em memoria.

## 8. Testes automatizados e qualidade

Os testes foram escritos com `@QuarkusTest` e `RestAssured`. Isso garante que a API seja exercitada com HTTP real em ambiente de teste, e nao apenas com chamadas diretas a metodos.

Casos cobertos:

1. lista inicial vazia;
2. criacao de mensagem;
3. busca de id valido;
4. exclusao de mensagem;
5. busca de id inexistente;
6. busca apos exclusao.

Resultado local:

- `Tests run: 6`
- `Failures: 0`
- `Errors: 0`
- `BUILD SUCCESS`

## 9. Evidencias para a defesa

As evidencias textuais foram organizadas de forma reutilizavel para apresentacao:

- `docs/evidencias/terminal/`: capturas reais do terminal com respostas HTTP.
- `docs/evidencias/postman/`: roteiro de validacao usando a collection exportada.
- `docs/postman/`: collection e environment prontos para importacao.

Sequencia recomendada na apresentacao:

1. Mostrar `GET /mensagens` inicial com lista vazia.
2. Mostrar `POST /mensagens` com `201 Created`.
3. Mostrar `GET /mensagens/1` com `200 OK`.
4. Mostrar `GET /mensagens/999` com `404 Not Found`.
5. Mostrar `DELETE /mensagens/1` com `200 OK`.
6. Mostrar `GET /mensagens/1` apos remocao com `404 Not Found`.

## 10. Comparacao academica: Quarkus vs Spring Boot

### Similaridades

- Ambos permitem criar APIs REST com anotacoes conhecidas.
- Ambos suportam testes de integracao, serializacao JSON e injetam dependencias.

### Diferencas relevantes

- Quarkus e fortemente orientado a inicializacao rapida e eficiencia em cloud-native.
- Spring Boot possui ecossistema muito amplo e consolidado, mas geralmente com runtime mais pesado.
- Para uma defesa curta, Quarkus ajuda porque sobe rapido e deixa o ciclo de demonstracao mais eficiente.

## 11. Limitacoes conscientes

O projeto nao tenta resolver problemas que ficariam fora do objetivo principal:

- nao ha persistencia em banco;
- nao ha seguranca;
- nao ha mensageria assincrona;
- nao ha tolerancia a falhas distribuida;
- nao ha orquestracao com outros servicos.

Essas limitacoes sao coerentes com a proposta didatica.

## 12. Possiveis evolucoes futuras

Se o projeto precisasse sair do nivel academico para algo mais proximo de producao, os proximos passos naturais seriam:

1. persistir mensagens em banco de dados;
2. validar campos obrigatorios;
3. adicionar tratamento uniforme de erros;
4. documentar a API com OpenAPI;
5. integrar broker real para mensageria assincrona;
6. adicionar autenticacao e auditoria.

## 13. Referencias oficiais

- <https://quarkus.io/guides/getting-started>
- <https://quarkus.io/extensions/io.quarkus/quarkus-rest-jackson/>
- <https://quarkus.io/guides/getting-started-testing>
- <https://quarkus.io/guides/resteasy>
