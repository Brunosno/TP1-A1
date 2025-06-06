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
import main.dto.fabricanteDTO.FabricanteDTO;
import main.dto.fabricanteDTO.FabricanteResponseDTO;
import main.service.fabricante.FabricanteService;

@Path("Fabricantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FabricanteResouce {

    @Inject
    FabricanteService service;

    @GET
    public Response buscarTodos() {
        return Response.ok().entity(service.findAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        FabricanteResponseDTO fabricante = service.findById(id);
        if (fabricante != null) {
            return Response.ok(fabricante).build();
        }
        return Response.status(Status.NOT_FOUND).build();
    }

    @GET
    @Path("/cnpj/{cnpj}")
    public Response buscarPorCNPJ(@PathParam("cnpj") String cnpj) {
        FabricanteResponseDTO fabricante = service.findByCNPJ(cnpj);
        if (fabricante != null) {
            return Response.ok(fabricante).build();
        }
        return Response.status(Status.NOT_FOUND).build();
    }

    @POST
    public Response incluir(FabricanteDTO dto) {
        FabricanteResponseDTO fabricante = service.create(dto);
        return Response.status(Status.CREATED).entity(fabricante).build();
    }

    @PUT
    @Path("/{id}")
    public Response alterar(@PathParam("id") Long id, FabricanteDTO dto) {
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
