package main.dto.usuarioDTO;

import main.model.usuario.Perfil;
import main.model.usuario.Usuario;

public record UsuarioResponseDTO(
    Long id,
    String nome,
    String username,
    Perfil perfil) {

    public static UsuarioResponseDTO valueOf(Usuario usuario) {
        if (usuario == null)
            return null;
        return new UsuarioResponseDTO(
            usuario.getId(), 
            usuario.getCliente().getNome(), 
            usuario.getUsername(), 
            usuario.getPerfil());
    }
    
}
