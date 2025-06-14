package main.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import main.dto.authDTO.AuthDTO;
import main.dto.usuarioDTO.UsuarioResponseDTO;
import main.service.hashpassword.HashService;
import main.service.jwt.JwtService;
import main.service.usuario.UsuarioService;

import org.jboss.logging.Logger;

@Path("auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    private static final Logger LOG = Logger.getLogger(AuthResource.class);

    @Inject
    HashService hashService;

    @Inject
    JwtService jwtService;

    @Inject
    UsuarioService usuarioService;

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(AuthDTO dto) {
        LOG.infof("Tentativa de login para usuário: %s", dto.username());
        String hash = null;
        try {
            hash = hashService.getHashSenha(dto.senha());
            LOG.debug("Hash da senha gerada com sucesso.");
        } catch (Exception e) {
            LOG.error("Erro ao gerar hash da senha: " + e.getMessage(), e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }

        UsuarioResponseDTO usuario = usuarioService.findByUsernameAndSenha(dto.username(), hash);

        if (usuario == null){ 
        LOG.warnf("Falha no login: usuário ou senha inválidos para %s", dto.username());
          return Response.noContent().build();
        }

        String token = jwtService.generateJwt(usuario.username(), usuario.perfil().getNome());
        LOG.infof("Login bem sucedido para usuário: %s", dto.username());
        return Response.ok().header("Authorization", token).entity(usuario).build();     
    }
}