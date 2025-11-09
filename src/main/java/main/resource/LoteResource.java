package main.resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import org.jboss.logging.Logger;
import main.dto.loteDTO.LoteDTO;
import main.dto.loteDTO.LoteResponseDTO;
import main.service.lote.LoteService;

import java.util.List;

@Path("lotes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoteResource {

    private static final Logger LOG = Logger.getLogger(LoteResource.class);

    @Inject
    LoteService service;

    @GET
    public Response buscarTodos() {
        LOG.info("Buscando todos os lotes.");
        List<LoteResponseDTO> lotes = service.findAll();
        return Response.ok(lotes).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        LOG.infof("Buscando lote com ID: %d", id);
        LoteResponseDTO lote = service.findById(id);
        return Response.ok(lote).build();
    }

    @POST
    public Response incluir(LoteDTO dto) {
        LOG.infof("Incluindo novo lote: Descricao=%s, Quantidade=%d", dto.descricao(), dto.quantidade());
        LoteResponseDTO novo = service.create(dto);
        return Response.status(Status.CREATED).entity(novo).build();
    }

    @PUT
    @Path("/{id}")
    public Response alterar(@PathParam("id") Long id, LoteDTO dto) {
        LOG.infof("Atualizando lote com ID: %d", id);
        service.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response apagar(@PathParam("id") Long id) {
        LOG.infof("Apagando lote com ID: %d", id);
        service.delete(id);
        return Response.noContent().build();
    }
}
