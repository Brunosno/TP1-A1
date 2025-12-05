package main.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import main.dto.pedidoDTO.PedidoDTO;
import main.dto.pedidoDTO.PedidoResponseDTO;
import main.service.pedido.PedidoService;

import org.jboss.logging.Logger;

import io.quarkus.security.Authenticated;

import java.util.List;

@Authenticated
@Path("/pedidos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PedidoResource {

    private static final Logger LOG = Logger.getLogger(PedidoResource.class);

    @Inject
    PedidoService pedidoService;

    @GET
    @RolesAllowed({"Adm"})
    public List<PedidoResponseDTO> BuscarTodos() {
        LOG.info("Buscando todos os pedidos.");
        return pedidoService.findAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"Adm", "User"})
    public PedidoResponseDTO BuscarPorId(@PathParam("id") Long id) {
        LOG.infof("Buscando pedido com ID: %d", id);
        return pedidoService.findById(id);
    }

    @GET
    @Path("/usuario/{idUsuario}")
    @RolesAllowed({"Adm"})
    public List<PedidoResponseDTO> buscarPorUsuario(@PathParam("idUsuario") Long idUsuario) {
        LOG.infof("Buscando pedidos do usuario com ID: %d", idUsuario);
        return pedidoService.findByUsuario(idUsuario);
    }
    
    @POST
    @RolesAllowed({"User"})
    public Response incluir(PedidoDTO dto) {
        LOG.infof("Incluindo novo pedido para usuario ID: %d", dto.idUsuario());
        PedidoResponseDTO pedido = pedidoService.create(dto);
        LOG.debugf("Pedido usuario com ID: %d", pedido.id());
        return Response.status(Response.Status.CREATED).entity(pedido).build();
    }


    @PUT
    @Path("/{id}")
    @RolesAllowed({"Adm"})
    public Response atualizar(@PathParam("id") Long id, PedidoDTO dto) {
        LOG.infof("Atualizando pedido com ID: %d", id);
        PedidoResponseDTO response = pedidoService.update(id, dto);
        LOG.debug("Pedido atualizado com sucesso.");
        return Response.ok(response).build();
    }

    @DELETE
    @RolesAllowed({"Adm", "User"})
    @Path("/{id}")
    public Response apagar(@PathParam("id") Long id) {
        LOG.infof("Apagando pedido com ID: %d", id);
        pedidoService.delete(id);
        LOG.debug("Pedido removido com sucesso.");
        return Response.noContent().build();
    }
}
