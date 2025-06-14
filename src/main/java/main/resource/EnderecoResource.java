package main.resource;

import java.util.List;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import main.dto.enderecoDTO.EnderecoDTO;
import main.dto.enderecoDTO.EnderecoResponseDTO;
import main.service.endereco.EnderecoService;

import org.jboss.logging.Logger;

@Path("enderecos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EnderecoResource {

    private static final Logger LOG = Logger.getLogger(EnderecoResource.class);

    @Inject
    EnderecoService enderecoService;

    @POST
    public Response incluir(EnderecoDTO dto) {
        LOG.infof("Incluindo novo endereço");
        EnderecoResponseDTO endereco = enderecoService.create(dto);
        LOG.debugf("Endereço criado com ID: %d", endereco.id());
        return Response.status(Status.CREATED).entity(endereco).build();
    }

    @GET
    @RolesAllowed({"Adm"})
    public List<EnderecoResponseDTO> buscarTodos() {
        LOG.info("Listando todos os endereços.");
        return enderecoService.findAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"Adm", "User"})
    public Response buscarPorId(@PathParam("id") Long id) {
        LOG.infof("Buscando endereço com ID: %d", id);
        EnderecoResponseDTO endereco = enderecoService.findById(id);
        if (endereco != null) {
            LOG.debugf("Endereço encontrado");
            return Response.ok(endereco).build();
        }
        LOG.warnf("Endereço com ID %d não encontrado.", id);
        return Response.status(Status.NOT_FOUND).build();
    }

    @GET
    @Path("/cep/{cep}")
    @RolesAllowed({"Adm"})
    public Response buscarPorCEP(@PathParam("cep") String cep) {
        LOG.infof("Buscando endereço com cep: %d", cep);
        EnderecoResponseDTO endereco = enderecoService.findByCEP(cep);
        if (endereco != null) {
            LOG.debugf("Endereço encontrado");
            return Response.ok(endereco).build();
        }
        LOG.warnf("Endereço com CEP %d não encontrado.", cep);
        return Response.status(Status.NOT_FOUND).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"Adm", "User"})
    public Response alterar(@PathParam("id") Long id, EnderecoDTO dto) {
        LOG.infof("Atualizando endereço com ID: %d", id);
        enderecoService.update(id, dto);
        LOG.debug("Endereço atualizado com sucesso.");
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"Adm", "User"})
    public Response apagar(@PathParam("id") Long id) {
        LOG.infof("Apagando endereço com ID: %d", id);
        enderecoService.delete(id);
        LOG.debug("Endereço removido com sucesso.");
        return Response.noContent().build();
    }
}
