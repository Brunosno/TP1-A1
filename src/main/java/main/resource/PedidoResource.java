package main.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import main.dto.pedidoDTO.PedidoDTO;
import main.dto.pedidoDTO.PedidoResponseDTO;
import main.service.pedido.PedidoService;

import java.util.List;

@Path("/pedidos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PedidoResource {

    @Inject
    PedidoService pedidoService;

    @GET
    public List<PedidoResponseDTO> BuscarTodos() {
        return pedidoService.findAll();
    }

    @GET
    @Path("/{id}")
    public PedidoResponseDTO BuscarPorId(@PathParam("id") Long id) {
        return pedidoService.findById(id);
    }

    @GET
    @Path("/cliente/{idCliente}")
    public List<PedidoResponseDTO> buscarPorCliente(@PathParam("idCliente") Long idCliente) {
        return pedidoService.findByClienteId(idCliente);
    }
    
    @POST
    public Response incluir(PedidoDTO dto) {
        PedidoResponseDTO response = pedidoService.create(dto);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }


    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, PedidoDTO dto) {
        PedidoResponseDTO response = pedidoService.update(id, dto);
        return Response.ok(response).build();
    }

    @DELETE
    @Path("/{id}")
    public Response apagar(@PathParam("id") Long id) {
        pedidoService.delete(id);
        return Response.noContent().build();
    }
}
