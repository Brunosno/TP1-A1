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
import main.dto.telefoneDTO.TelefoneDTO;
import main.dto.telefoneDTO.TelefoneResponseDTO;
import main.service.telefone.TelefoneService;

import org.jboss.logging.Logger;

@Path("telefones")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TelefoneResource {

    private static final Logger LOG = Logger.getLogger(TelefoneResource.class);
    
    @Inject
    TelefoneService service;

    @GET
    @RolesAllowed({"Adm"})
    public Response buscarTodos(){
        LOG.info("Buscando todos os telefones.");
        return Response.ok().entity(service.findAll()).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"Adm"})
    public Response buscarPorId(@PathParam("id") Long id) {
        LOG.infof("Buscando telefone com ID: %d", id);
        TelefoneResponseDTO telefone = service.findById(id);
        if (telefone != null) {
            LOG.debug("Telefone encontrado.");
            return Response.ok(telefone).build();
        }
        LOG.warn("Telefone não encontrado.");
        return Response.status(Status.NOT_FOUND).build();
    }

    @GET
    @Path("/numero/{numero}")
    @RolesAllowed({"Adm"})
    public Response buscarPorNumero(@PathParam("numero") String numero) {
        LOG.infof("Buscando telefone com número: %s", numero);
        TelefoneResponseDTO telefone = service.findByNumber(numero);
        if (telefone != null) {
            LOG.debug("Telefone encontrado.");
            return Response.ok(telefone).build();
        }
        LOG.warn("Telefone não encontrado.");
        return Response.status(Status.NOT_FOUND).build();
    }

    @POST
    @RolesAllowed({"Adm", "User"})
    @Transactional
    public Response incluir(TelefoneDTO dto) {
        LOG.infof("Incluindo novo telefone: %s", dto.numero());
        TelefoneResponseDTO telefone = service.create(dto);
         LOG.debugf("Telefone criado com ID: %d", telefone.id());
        return Response.status(Status.CREATED).entity(telefone).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"Adm", "User"})
    public Response alterar(@PathParam("id") Long id, TelefoneDTO dto) {
        LOG.infof("Alterando telefone com ID: %d", id);
        service.update(id, dto);
        LOG.debug("Telefone alterado com sucesso.");
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"Adm", "User"})
    @Transactional
    public Response apagar(@PathParam("id") Long id) {
        LOG.infof("Apagando telefone com ID: %d", id);
        service.delete(id);
        LOG.debug("Telefone removido com sucesso.");
        return Response.noContent().build();
    }
}
