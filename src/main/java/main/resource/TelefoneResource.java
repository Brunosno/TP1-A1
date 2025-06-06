package main.resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import main.dto.telefoneDTO.TelefoneDTO;
import main.dto.telefoneDTO.TelefoneResponseDTO;
import main.service.telefone.TelefoneService;

@Path("Telefones")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TelefoneResource {
    
    @Inject
    TelefoneService service;

    @GET
    public Response buscarTodos(){
        return Response.ok().entity(service.findAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(Long id) {
        TelefoneResponseDTO telefone = service.findById(id);
        if (telefone != null) {
            return Response.ok(telefone).build();
        }
        return Response.status(Status.NOT_FOUND).build();
    }

    @GET
    @Path("/Numero/{numero}")
    public Response buscarPorNumero(String numero) {
        TelefoneResponseDTO telefone = service.findByNumber(numero);
        if (telefone != null) {
            return Response.ok(telefone).build();
        }
        return Response.status(Status.NOT_FOUND).build();
    }

    @POST
    public Response incluir(TelefoneDTO dto) {
        TelefoneResponseDTO telefone = service.create(dto);
        return Response.status(Status.CREATED).entity(telefone).build();
    }

    @PUT
    @Path("/{id}")
    public Response alterar(Long id, TelefoneDTO dto) {
        service.update(id, dto);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response apagar(Long id) {
        service.delete(id);
        return Response.noContent().build();
    }
}
