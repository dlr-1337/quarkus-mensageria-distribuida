package br.com.otavi.quarkus.mensageria;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

@QuarkusTest
@TestHTTPEndpoint(MensagemResource.class)
class MensagemResourceTest {

    @Inject
    MensagemService mensagemService;

    @BeforeEach
    void limparEstado() {
        mensagemService.limpar();
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoExistiremMensagens() {
        when()
                .get()
                .then()
                .statusCode(200)
                .body("$", empty());
    }

    @Test
    void deveCriarMensagemComIdTimestampELocation() {
        given()
                .contentType(ContentType.JSON)
                .body(novoPayload("alice", "Ola do POST"))
                .when()
                .post()
                .then()
                .statusCode(201)
                .header("Location", endsWith("/mensagens/1"))
                .body("id", is(1))
                .body("remetente", is("alice"))
                .body("conteudo", is("Ola do POST"))
                .body("timestamp", not(emptyOrNullString()));
    }

    @Test
    void deveBuscarMensagemPorId() {
        int id = criarMensagem("bob", "Mensagem para busca");

        when()
                .get("/{id}", id)
                .then()
                .statusCode(200)
                .body("id", is(id))
                .body("remetente", is("bob"))
                .body("conteudo", is("Mensagem para busca"));
    }

    @Test
    void deveExcluirMensagemPorId() {
        int id = criarMensagem("carol", "Mensagem para exclusao");

        when()
                .delete("/{id}", id)
                .then()
                .statusCode(200)
                .body("id", is(id))
                .body("remetente", is("carol"))
                .body("conteudo", is("Mensagem para exclusao"));
    }

    @Test
    void deveRetornar404QuandoBuscarIdInexistente() {
        when()
                .get("/{id}", 999)
                .then()
                .statusCode(404);
    }

    @Test
    void deveRetornar404AposExcluirMensagem() {
        int id = criarMensagem("dave", "Mensagem temporaria");

        when().delete("/{id}", id).then().statusCode(200);

        when()
                .get("/{id}", id)
                .then()
                .statusCode(404);
    }

    private int criarMensagem(String remetente, String conteudo) {
        return given()
                .contentType(ContentType.JSON)
                .body(novoPayload(remetente, conteudo))
                .when()
                .post()
                .then()
                .statusCode(201)
                .extract()
                .path("id");
    }

    private Map<String, String> novoPayload(String remetente, String conteudo) {
        return Map.of(
                "remetente", remetente,
                "conteudo", conteudo);
    }
}
