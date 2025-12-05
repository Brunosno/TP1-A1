package main.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import main.dto.controleDTO.ControleForm;
import main.dto.controleDTO.ControleResponseDTO;
import main.repository.ControleRepository;
import main.service.controle.ControleService;

import org.jboss.logging.Logger;

@Path("controles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.MULTIPART_FORM_DATA)
public class ControleResource {

    private static final Logger LOG = Logger.getLogger(ControleResource.class);

    @Inject
    ControleService service;

    @Inject
    ControleRepository controleRepository;

    @GET
    public Response buscarTodos(
        @QueryParam("page") @DefaultValue("0") int page,
        @QueryParam("pageSize") @DefaultValue("7") int pageSize
    ) { 
        LOG.info("Requisição para buscar todos os controles.");
        return Response.ok(service.findAll(page, pageSize)).build();
    }

    @GET
    @Path("/count")
    public Response count(){
        return Response.ok(service.count()).build();
    }

    @GET
    @Path("/fabricante/{fabricante}")
    public Response buscarPorFabricante(
        @PathParam("fabricante") String fabricante,
        @QueryParam("page") @DefaultValue("0") int page,
        @QueryParam("pageSize") @DefaultValue("7") int pageSize
        ) { 
        LOG.infof("Buscando controles pelo fabricante: %s", fabricante);
        return Response.ok(service.findByFabricante(fabricante, page, pageSize)).build();
    }

    @GET
    @Path("/cor/{cor}")
    public Response buscarPorCor(@PathParam("cor") String cor) { 
        LOG.infof("Buscando controles pela cor: %s", cor);
        return Response.ok(service.findByCor(cor)).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"Adm", "User"})
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
    @Path("/criar")
    @RolesAllowed({"Adm"})
    @Transactional
    public Response criarComImagem(@BeanParam ControleForm dto) {
        try {
           LOG.info("Criando controle com imagem via upload multipart.");
           ControleResponseDTO controle = service.createImage(dto.toDTO(), dto.imagens);
           return Response.status(Status.CREATED).entity(controle).build();
        } catch (Exception e) {
            LOG.error("Erro ao criar controle com imagem", e);
            return Response.status(Status.BAD_REQUEST)
                    .entity(java.util.Map.of("erro", e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"Adm"})
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Transactional
    public Response alterar(@PathParam("id") Long id, @BeanParam ControleForm form) {
        try {
            LOG.infof("Atualizando controle com ID %d com possível upload de imagem.", id);
            ControleResponseDTO atualizado = service.updateImage(id, form.toDTO(), form.imagens);
            return Response.ok(atualizado).build();
        } catch (Exception e) {
            LOG.error("Erro ao atualizar controle com imagem", e);
            return Response.status(Status.BAD_REQUEST)
                    .entity(java.util.Map.of("erro", e.getMessage()))
                    .build();
        }
    }


    @DELETE
    @Path("/{id}")
    @RolesAllowed({"Adm"})
    @Transactional
    public Response apagar(@PathParam("id") Long id) {
        LOG.infof("Apagando controle com ID: %d", id);
        service.delete(id);
        LOG.debug("Controle removido com sucesso.");
        return Response.noContent().build();
    }
}
