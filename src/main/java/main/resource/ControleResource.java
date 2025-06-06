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
import main.dto.controleDTO.ControleDTO;
import main.service.controle.ControleService;

@Path("controles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ControleResource {

    @Inject
    ControleService service;

    @GET
    public Response buscarTodos() { 
        return Response.ok(service.findAll()).build();
    }

    @GET
    @Path("/fabricante/{fabricante}")
    public Response buscarPorMarca(String fabricante) { 
        return Response.ok(service.findByFabricante(fabricante)).build();
    }

    @GET
    @Path("/cor/{cor}")
    public Response buscarPorCor(String cor) { 
        return Response.ok(service.findByCor(cor)).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(Long id) { 
        return Response.ok(service.findById(id)).build();
    }

    @POST
    public Response incluir(ControleDTO dto) {
        return Response.status(Status.CREATED).entity(service.create(dto)).build();
    }

    @PUT
    @Path("/{id}")
    public void alterar(Long id, ControleDTO dto) {
        service.update(id, dto);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void apagar(Long id) {
        service.delete(id);
    }

}
