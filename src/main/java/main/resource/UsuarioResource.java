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

@Path("Usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject
    JsonWebToken jwt;

    @Inject
    UsuarioService usuarioService;

    @POST
    @Transactional
    public Response create(UsuarioDTO dto) {
        try {
            UsuarioResponseDTO usuarioCriado = usuarioService.create(dto);
            return Response.status(Response.Status.CREATED).entity(usuarioCriado).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao criar usuário: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/perfil")
    public Response buscarUsuarioLogado() { 

        String username = jwt.getSubject();

        UsuarioResponseDTO usuario = usuarioService.findByUsername(username);

        return Response.ok().entity(usuario).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        UsuarioResponseDTO usuario = usuarioService.findById(id);
        if (usuario == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(usuario).build();
    }

    @GET
    @RolesAllowed({"Adm"})
    public Response findAll() {
        List<UsuarioResponseDTO> usuarios = usuarioService.findAll();
        return Response.ok(usuarios).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, UsuarioDTO dto) {
        try {
            usuarioService.update(id, dto);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao atualizar usuário").build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        usuarioService.delete(id);
        return Response.noContent().build();
    }
}