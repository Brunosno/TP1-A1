package main.resource;

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

@Path("clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResource {

    @Inject
    ClienteService service;

    @GET
    public Response buscarTodos() { 
        return Response.ok().entity(service.findAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) { 
        ClienteResponseDTO cliente = service.findById(id);
        if (cliente != null) {
            return Response.ok(cliente).build();
        }
        return Response.status(Status.NOT_FOUND).build();
    }

    @GET
    @Path("/cpf/{cpf}")
    public Response buscarPorCPF(@PathParam("cpf") String cpf) { 
        ClienteResponseDTO cliente = service.findByCPF(cpf);
        if (cliente != null) {
            return Response.ok(cliente).build();
        }
        return Response.status(Status.NOT_FOUND).build();
    }

    @POST
    public Response incluir(ClienteDTO dto) {
        ClienteResponseDTO cliente = service.create(dto);
        return Response.status(Status.CREATED).entity(cliente).build();
    }

    @PUT
    @Path("/{id}")
    public Response alterar(@PathParam("id") Long id, ClienteDTO dto) {
        service.update(id, dto);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response apagar(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }
}
