package main.resource;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import main.dto.enderecoDTO.EnderecoDTO;
import main.dto.enderecoDTO.EnderecoResponseDTO;
import main.service.endereco.EnderecoService;

@Path("enderecos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EnderecoResource {

    @Inject
    EnderecoService enderecoService;

    @POST
    public Response incluir(EnderecoDTO dto) {
        EnderecoResponseDTO endereco = enderecoService.create(dto);
        return Response.status(Status.CREATED).entity(endereco).build();
    }

    @GET
    public List<EnderecoResponseDTO> buscarTodos() {
        return enderecoService.findAll();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        return Response.ok(enderecoService.findById(id)).build();
    }

    @PUT
    @Path("/{id}")
    public Response alterar(@PathParam("id") Long id, EnderecoDTO dto) {
        enderecoService.update(id, dto);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response apagar(@PathParam("id") Long id) {
        enderecoService.delete(id);
        return Response.noContent().build();
    }
}
