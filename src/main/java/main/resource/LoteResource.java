package main.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import org.jboss.logging.Logger;

import io.quarkus.security.Authenticated;
import main.dto.loteDTO.LoteDTO;
import main.dto.loteDTO.LoteResponseDTO;
import main.service.lote.LoteService;

import java.util.List;

@Authenticated
@Path("lotes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoteResource {

    private static final Logger LOG = Logger.getLogger(LoteResource.class);

    @Inject
    LoteService service;

    @GET
    @RolesAllowed({"Adm"})
    public Response buscarTodos(
        @QueryParam("page") @DefaultValue("0") int page,
        @QueryParam("pageSize") @DefaultValue("7") int pageSize
    ) {
        LOG.info("Buscando todos os lotes.");
        List<LoteResponseDTO> lotes = service.findAll(page, pageSize);
        return Response.ok(lotes).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"Adm"})
    public Response buscarPorId(@PathParam("id") Long id) {
        LOG.infof("Buscando lote com ID: %d", id);
        LoteResponseDTO lote = service.findById(id);
        return Response.ok(lote).build();
    }

    @POST
    @RolesAllowed({"Adm"})
    public Response incluir(LoteDTO dto) {
        LOG.infof("Incluindo novo lote: Descricao=%s, Quantidade=%d", dto.descricao(), dto.quantidade());
        LoteResponseDTO novo = service.create(dto);
        return Response.status(Status.CREATED).entity(novo).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"Adm"})
    public Response alterar(@PathParam("id") Long id, LoteDTO dto) {
        LOG.infof("Atualizando lote com ID: %d", id);
        service.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"Adm"})
    @Transactional
    public Response apagar(@PathParam("id") Long id) {
        LOG.infof("Apagando lote com ID: %d", id);
        service.delete(id);
        return Response.noContent().build();
    }

    @GET
    @RolesAllowed({"Adm"})
    @Path("/count")
    public Response count(){
        return Response.ok(service.count()).build();
    }
}
