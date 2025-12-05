package main.resource;

import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import main.dto.plataformaDTO.PlataformaDTO;
import main.dto.plataformaDTO.PlataformaResponseDTO;
import main.service.plataforma.PlataformaService;
import org.jboss.logging.Logger;

@PermitAll
@Path("plataformas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PlataformaResource {

    private static final Logger LOG = Logger.getLogger(PlataformaResource.class);

    @Inject
    PlataformaService service;

    @GET
    public Response buscarTodos(
        @QueryParam("page") @DefaultValue("0") int page,
        @QueryParam("pageSize") @DefaultValue("7") int pageSize
    ) {
        LOG.info("Requisição para buscar todas as plataformas paginadas.");
        return Response.ok(service.findAll(page, pageSize)).build();
    }

    @GET
    @Path("/count")
    public Response count() {
        return Response.ok(service.count()).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        LOG.infof("Buscando plataforma com ID: %d", id);
        PlataformaResponseDTO plataforma = service.findById(id);
        return Response.ok(plataforma).build();
    }

    @POST
    public Response incluir(PlataformaDTO dto) {
        LOG.infof("Incluindo nova plataforma: Nome=%s", dto.nome());
        PlataformaResponseDTO nova = service.create(dto);
        return Response.status(Status.CREATED).entity(nova).build();
    }

    @PUT
    @Path("/{id}")
    public Response alterar(@PathParam("id") Long id, PlataformaDTO dto) {
        LOG.infof("Atualizando plataforma com ID: %d", id);
        service.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response apagar(@PathParam("id") Long id) {
        LOG.infof("Apagando plataforma com ID: %d", id);
        service.delete(id);
        return Response.noContent().build();
    }
}
