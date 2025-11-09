package main.resource;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import main.dto.enderecoDTO.EnderecoDTO;
import main.dto.enderecoDTO.EnderecoResponseDTO;
import main.service.endereco.EnderecoServiceImpl;
import org.jboss.logging.Logger;

@Path("enderecos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EnderecoResource {

    private static final Logger LOG = Logger.getLogger(EnderecoResource.class);

    @Inject
    EnderecoServiceImpl enderecoService;

    @POST
    public Response incluir(EnderecoDTO dto) {
        LOG.infof("Incluindo novo endereço");
        EnderecoResponseDTO endereco = enderecoService.create(dto);
        LOG.debugf("Endereço criado com ID: %d", endereco.id());
        return Response.status(Status.CREATED).entity(endereco).build();
    }

    @GET
    public Response buscarTodos(@QueryParam("page") Integer page, @QueryParam("pageSize") Integer pageSize) {
        LOG.info("Listando todos os endereços.");

        if (page != null && pageSize != null) {
            List<EnderecoResponseDTO> lista = enderecoService.findAll(page, pageSize);
            LOG.debugf("Listando endereços paginados: página %d, tamanho %d", page, pageSize);
            return Response.ok(lista).build();
        }

        List<EnderecoResponseDTO> lista = enderecoService.findAll();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/count")
    public Response count() {
        long total = enderecoService.count();
        return Response.ok(total).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        LOG.infof("Buscando endereço com ID: %d", id);
        EnderecoResponseDTO endereco = enderecoService.findById(id);
        return Response.ok(endereco).build();
    }

    @GET
    @Path("/cep/{cep}")
    public Response buscarPorCEP(@PathParam("cep") String cep) {
        LOG.infof("Buscando endereço com cep: %s", cep);
        EnderecoResponseDTO endereco = enderecoService.findByCEP(cep);
        return Response.ok(endereco).build();
    }

    @PUT
    @Path("/{id}")
    public Response alterar(@PathParam("id") Long id, EnderecoDTO dto) {
        LOG.infof("Atualizando endereço com ID: %d", id);
        enderecoService.update(id, dto);
        LOG.debug("Endereço atualizado com sucesso.");
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response apagar(@PathParam("id") Long id) {
        LOG.infof("Apagando endereço com ID: %d", id);
        enderecoService.delete(id);
        LOG.debug("Endereço removido com sucesso.");
        return Response.noContent().build();
    }
}
