package main.resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
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
    public Response buscarPorMarca(@PathParam("fabricante") String fabricante) { 
        return Response.ok(service.findByFabricante(fabricante)).build();
    }

    @GET
    @Path("/cor/{cor}")
    public Response buscarPorCor(@PathParam("cor") String cor) { 
        return Response.ok(service.findByCor(cor)).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) { 
        return Response.ok(service.findById(id)).build();
    }

    @POST
    public Response incluir(ControleDTO dto) {
        return Response.status(Status.CREATED).entity(service.create(dto)).build();
    }

    @PUT
    @Path("/{id}")
    public Response alterar(@PathParam("id") Long id, ControleDTO dto) {
        service.update(id, dto);
        return Response.noContent().build(); // 204 No Content
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response apagar(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }
}
