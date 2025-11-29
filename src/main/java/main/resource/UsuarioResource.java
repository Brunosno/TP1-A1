package main.resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import main.dto.usuarioDTO.UsuarioDTO;
import main.dto.usuarioDTO.UsuarioResponseDTO;
import main.service.usuario.UsuarioService;

import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;

import jakarta.annotation.security.RolesAllowed;

import org.jboss.logging.Logger;

@Path("usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    private static final Logger LOG = Logger.getLogger(UsuarioResource.class);

    @Inject
    JsonWebToken jwt;

    @Inject
    UsuarioService usuarioService;

    @POST
    @Transactional
    public Response incluir(UsuarioDTO dto) {
        LOG.infof("Criando novo usuário: %s", dto.username());
        try {
            UsuarioResponseDTO usuarioCriado = usuarioService.create(dto);
            LOG.debugf("Usuário criado com sucesso: %s", usuarioCriado.username());
            return Response.status(Response.Status.CREATED).entity(usuarioCriado).build();
        } catch (Exception e) {
            LOG.error("Erro ao criar usuário: " + e.getMessage(), e);
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao criar usuário: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/perfil")
    //@RolesAllowed({"User", "Adm"})
    public Response buscarUsuarioLogado() { 

        String username = jwt.getSubject();
        LOG.infof("Buscando usuário logado: %s", username);
        UsuarioResponseDTO usuario = usuarioService.findByUsername(username);
        if (usuario == null) {
                    LOG.warnf("Usuário logado não encontrado: %s", username);
                    return Response.status(Response.Status.NOT_FOUND).build();
                }
        LOG.debugf("Usuário logado encontrado: %s", username);
        return Response.ok(usuario).build();
    }

    @GET
    @Path("/{id}")
    //@RolesAllowed({"Adm"})
    public Response buscarPorId(@PathParam("id") Long id) {
        LOG.infof("Buscando usuário por ID: %d", id);
        UsuarioResponseDTO usuario = usuarioService.findById(id);
        if (usuario == null) {
            LOG.warnf("Usuário não encontrado para ID: %d", id);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        LOG.debugf("Usuário encontrado para ID: %d", id);
        return Response.ok(usuario).build();
    }

    @GET
    //@RolesAllowed({"Adm"})
    public Response buscarTodos() {
        LOG.info("Buscando todos os usuários");
        List<UsuarioResponseDTO> usuarios = usuarioService.findAll();
        LOG.debugf("Quantidade de usuários encontrados: %d", usuarios.size());
        return Response.ok(usuarios).build();
    }

    @PUT
    @Path("/{id}")
    //@RolesAllowed({"Adm"})
    @Transactional
    public Response atualizar(@PathParam("id") Long id, UsuarioDTO dto) {
        LOG.infof("Atualizando usuário ID: %d", id);
        try {
            usuarioService.update(id, dto);
            LOG.debugf("Usuário atualizado com sucesso ID: %d", id);
            return Response.noContent().build();
        } catch (Exception e) {
            LOG.errorf(e, "Erro ao atualizar usuário ID: %d", id);
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao atualizar usuário").build();
        }
    }

    @DELETE
    @Path("/{id}")
    //@RolesAllowed({"Adm"})
    @Transactional
    public Response apagar(@PathParam("id") Long id) {
        LOG.infof("Deletando usuário ID: %d", id);
        usuarioService.delete(id);
        LOG.debugf("Usuário deletado ID: %d", id);
        return Response.noContent().build();
    }
}