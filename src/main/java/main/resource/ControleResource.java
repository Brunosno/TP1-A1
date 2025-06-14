package main.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import main.dto.controleDTO.ControleDTO;
import main.dto.controleDTO.ControleResponseDTO;
import main.service.controle.ControleService;

import org.jboss.logging.Logger;

@Path("controles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ControleResource {

    private static final Logger LOG = Logger.getLogger(ControleResource.class);

    @Inject
    ControleService service;

    @GET
    public Response buscarTodos() { 
        LOG.info("Requisição para buscar todos os controles.");
        return Response.ok(service.findAll()).build();
    }

    @GET
    @Path("/fabricante/{fabricante}")
    public Response buscarPorMarca(@PathParam("fabricante") String fabricante) { 
        LOG.infof("Buscando controles pelo fabricante: %s", fabricante);
        return Response.ok(service.findByFabricante(fabricante)).build();
    }

    @GET
    @Path("/cor/{cor}")
    public Response buscarPorCor(@PathParam("cor") String cor) { 
        LOG.infof("Buscando controles pela cor: %s", cor);
        return Response.ok(service.findByCor(cor)).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) { 
        LOG.infof("Buscando controle com ID: %d", id);
        ControleResponseDTO controle = service.findById(id);
        if (controle != null) {
            LOG.debugf("Controle encontrado: %s", controle.nome());
            return Response.ok(controle).build();
        }
        LOG.warnf("Controle com ID %d não encontrado.", id);
        return Response.status(Status.NOT_FOUND).build();
    }

    @POST
    @RolesAllowed({"Adm", "User"})
    public Response incluir(ControleDTO dto) {
        LOG.infof("Incluindo novo controle: Modelo=%s", dto.nome());
        ControleResponseDTO novo = service.create(dto);
        LOG.debugf("Controle criado com ID: %d", novo.id());
        return Response.status(Status.CREATED).entity(novo).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"Adm", "User"})
    public Response alterar(@PathParam("id") Long id, ControleDTO dto) {
        LOG.infof("Atualizando controle com ID: %d", id);
        service.update(id, dto);
        LOG.debug("Controle atualizado com sucesso.");
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"Adm", "User"})
    @Transactional
    public Response apagar(@PathParam("id") Long id) {
        LOG.infof("Apagando controle com ID: %d", id);
        service.delete(id);
        LOG.debug("Controle removido com sucesso.");
        return Response.noContent().build();
    }
}
