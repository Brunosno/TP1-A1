package main.resource;

import jakarta.annotation.security.RolesAllowed;
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

@Path("plataformas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PlataformaResource {

    private static final Logger LOG = Logger.getLogger(PlataformaResource.class);

    @Inject
    PlataformaService service;

    @GET
    public Response buscarTodos() {
        LOG.info("Requisição para buscar todas as plataformas.");
        return Response.ok(service.findAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        LOG.infof("Buscando plataforma com ID: %d", id);
        PlataformaResponseDTO plataforma = service.findById(id);
        if (plataforma != null) {
            LOG.debugf("Plataforma encontrada: %s", plataforma.nome());
            return Response.ok(plataforma).build();
        }
        LOG.warnf("Plataforma com ID %d não encontrada.", id);
        return Response.status(Status.NOT_FOUND).build();
    }

    @POST
    @RolesAllowed({"Adm", "User"})
    public Response incluir(PlataformaDTO dto) {
        LOG.infof("Incluindo nova plataforma: Nome=%s", dto.nome());
        PlataformaResponseDTO nova = service.create(dto);
        LOG.debugf("Plataforma criada com ID: %d", nova.id());
        return Response.status(Status.CREATED).entity(nova).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"Adm", "User"})
    public Response alterar(@PathParam("id") Long id, PlataformaDTO dto) {
        LOG.infof("Atualizando plataforma com ID: %d", id);
        service.update(id, dto);
        LOG.debug("Plataforma atualizada com sucesso.");
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"Adm", "User"})
    @Transactional
    public Response apagar(@PathParam("id") Long id) {
        LOG.infof("Apagando plataforma com ID: %d", id);
        service.delete(id);
        LOG.debug("Plataforma removida com sucesso.");
        return Response.noContent().build();
    }
}
