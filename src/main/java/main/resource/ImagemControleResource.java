package main.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import org.jboss.logging.Logger;

import main.dto.imagemControleDTO.ImagemControleDTO;
import main.dto.imagemControleDTO.ImagemControleResponseDTO;
import main.service.imagemControle.ImagemControleService;

@Path("imagens")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ImagemControleResource {

    private static final Logger LOG = Logger.getLogger(ImagemControleResource.class);

    @Inject
    ImagemControleService service;

    @GET
    public Response buscarTodos() {
        LOG.info("Requisição para buscar todas as imagens de raquetes.");
        return Response.ok(service.findAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        LOG.infof("Buscando imagem de raquete com ID: %d", id);
        ImagemControleResponseDTO imagem = service.findById(id);
        if (imagem != null) {
            LOG.debugf("Imagem de raquete encontrada: %s", imagem.url());
            return Response.ok(imagem).build();
        }
        LOG.warnf("Imagem de raquete com ID %d não encontrada.", id);
        return Response.status(Status.NOT_FOUND).build();
    }

    @POST
    //@RolesAllowed({"Adm"})
    public Response incluir(ImagemControleDTO dto) {
        LOG.infof("Incluindo nova imagem de raquete: URL=%s", dto.url());
        ImagemControleResponseDTO nova = service.create(dto);
        LOG.debugf("Imagem de raquete criada com ID: %d", nova.id());
        return Response.status(Status.CREATED).entity(nova).build();
    }

    @PUT
    @Path("/{id}")
    //@RolesAllowed({"Adm"})
    public Response alterar(@PathParam("id") Long id, ImagemControleDTO dto) {
        LOG.infof("Atualizando imagem de raquete com ID: %d", id);
        service.update(id, dto);
        LOG.debug("Imagem de raquete atualizada com sucesso.");
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    //@RolesAllowed({"Adm"})
    @Transactional
    public Response apagar(@PathParam("id") Long id) {
        LOG.infof("Apagando imagem de raquete com ID: %d", id);
        service.delete(id);
        LOG.debug("Imagem de raquete removida com sucesso.");
        return Response.noContent().build();
    }
}
