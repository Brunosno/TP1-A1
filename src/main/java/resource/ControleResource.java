package resource;

import java.util.List;

import dto.ControleDTO;
import dto.ControleResponseDTO;
import service.ControleService;
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

@Path("Controles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ControleResource {

    @Inject
    ControleService service;

    @GET
    public List<ControleResponseDTO> buscarTodos() { 
        return service.findAll();
    }

    @GET
    @Path("/{marca}")
    public ControleResponseDTO buscarPorMarca(String marca) { 
        return service.findByMarca(marca);
    }

    @GET
    @Path("/{id}")
    public ControleResponseDTO buscarPorId(Long id) { 
        return service.findById(id);
    }

    @POST
    public ControleResponseDTO incluir(ControleDTO dto) {
        return service.create(dto);
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
