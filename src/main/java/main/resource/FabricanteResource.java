package main.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import main.dto.fabricanteDTO.FabricanteDTO;
import main.dto.fabricanteDTO.FabricanteResponseDTO;
import main.service.fabricante.FabricanteService;

import org.jboss.logging.Logger;

@Path("fabricantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FabricanteResource {

    private static final Logger LOG = Logger.getLogger(FabricanteResource.class);

    @Inject
    FabricanteService service;

    @GET
    public Response buscarTodos() {
        LOG.info("Buscando todos os fabricantes.");
        return Response.ok(service.findAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        LOG.infof("Buscando fabricante com ID: %d", id);
        FabricanteResponseDTO fabricante = service.findById(id);
        if (fabricante != null) {
            LOG.debugf("Fabricante encontrado: %s", fabricante.nome());
            return Response.ok(fabricante).build();
        }
        LOG.warnf("Fabricante com ID %d não encontrado.", id);
        return Response.status(Status.NOT_FOUND).build();
    }

    @GET
    @Path("/cnpj/{cnpj}")
    @RolesAllowed({"Adm"})
    public Response buscarPorCNPJ(@PathParam("cnpj") String cnpj) {
        LOG.infof("Buscando fabricante com CNPJ: %s", cnpj);
        FabricanteResponseDTO fabricante = service.findByCNPJ(cnpj);
        if (fabricante != null) {
            LOG.debugf("Fabricante encontrado: %s", fabricante.nome());
            return Response.ok(fabricante).build();
        }
        LOG.warnf("Fabricante com CNPJ %s não encontrado.", cnpj);
        return Response.status(Status.NOT_FOUND).build();
    }

    @POST
    @RolesAllowed({"Adm"})
    @Transactional
    public Response incluir(FabricanteDTO dto) {
        LOG.infof("Incluindo novo fabricante: Nome=%s, CNPJ=%s", dto.nome(), dto.cnpj());
        FabricanteResponseDTO fabricante = service.create(dto);
        LOG.debugf("Fabricante criado com ID: %d", fabricante.id());
        return Response.status(Status.CREATED).entity(fabricante).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"Adm"})
    public Response alterar(@PathParam("id") Long id, FabricanteDTO dto) {
        LOG.infof("Atualizando fabricante com ID: %d", id);
        service.update(id, dto);
        LOG.debug("Fabricante atualizado com sucesso.");
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"Adm"})
    @Transactional
    public Response apagar(@PathParam("id") Long id) {
        LOG.infof("Apagando fabricante com ID: %d", id);
        service.delete(id);
        LOG.debug("Fabricante removido com sucesso.");
        return Response.noContent().build();
    }
}
