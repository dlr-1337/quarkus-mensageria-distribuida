package br.com.otavi.quarkus.mensageria;

import java.net.URI;
import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("/mensagens")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MensagemResource {

    @Inject
    MensagemService mensagemService;

    @GET
    public List<Mensagem> listar() {
        return mensagemService.listar();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        return mensagemService.buscarPorId(id)
                .map(mensagem -> Response.ok(mensagem).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    public Response criar(Mensagem mensagemRecebida, @Context UriInfo uriInfo) {
        Mensagem mensagemSalva = mensagemService.criar(mensagemRecebida);
        URI location = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(mensagemSalva.getId()))
                .build();

        return Response.created(location)
                .entity(mensagemSalva)
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response remover(@PathParam("id") Long id) {
        return mensagemService.remover(id)
                .map(mensagem -> Response.ok(mensagem).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }
}
