package main.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import main.dto.clienteDTO.ClienteDTO;
import main.dto.clienteDTO.ClienteResponseDTO;
import main.service.cliente.ClienteService;
import org.jboss.logging.Logger;

import io.quarkus.security.Authenticated;

@Authenticated
@Path("clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResource {

    private static final Logger LOG = Logger.getLogger(ClienteResource.class);

    @Inject
    ClienteService service;

    @GET
    @RolesAllowed({"Adm"})
    public Response buscarTodos() { 
        LOG.info("Requisição recebida para listar todos os clientes.");
        return Response.ok().entity(service.findAll()).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"Adm"})
    public Response buscarPorId(@PathParam("id") Long id) { 
        LOG.infof("Buscando cliente com ID: %d", id);
        ClienteResponseDTO cliente = service.findById(id);
        if (cliente != null) {
            LOG.debugf("Cliente encontrado: %s", cliente.nome());
            return Response.ok(cliente).build();
        }
        LOG.warnf("Cliente com ID %d não encontrado.", id);
        return Response.status(Status.NOT_FOUND).build();
    }

    @GET
    @Path("/cpf/{cpf}")
    @RolesAllowed({"Adm"})
    public Response buscarPorCPF(@PathParam("cpf") String cpf) { 
        LOG.infof("Buscando cliente com CPF: %s", cpf);
        ClienteResponseDTO cliente = service.findByCPF(cpf);
        if (cliente != null) {
            LOG.debugf("Cliente encontrado: %s", cliente.nome());
            return Response.ok(cliente).build();
        }
        LOG.warnf("Cliente com CPF %s não encontrado.", cpf);
        return Response.status(Status.NOT_FOUND).build();
    }

    @POST
    public Response incluir(ClienteDTO dto) {
        LOG.infof("Incluindo novo cliente: %s", dto.nome());
        ClienteResponseDTO cliente = service.create(dto);
        LOG.debugf("Cliente criado com ID: %d", cliente.id());
        return Response.status(Status.CREATED).entity(cliente).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"Adm", "User"})
    public Response alterar(@PathParam("id") Long id, ClienteDTO dto) {
        LOG.infof("Alterando cliente com ID: %d", id);
        service.update(id, dto);
        LOG.debug("Cliente atualizado com sucesso.");
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"Adm", "User"})
    @Transactional
    public Response apagar(@PathParam("id") Long id) {
        LOG.infof("Apagando cliente com ID: %d", id);
        service.delete(id);
        LOG.debug("Cliente removido com sucesso.");
        return Response.noContent().build();
    }
}
