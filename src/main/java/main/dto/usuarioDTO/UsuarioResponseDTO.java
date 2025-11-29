package main.dto.usuarioDTO;

import java.util.List;

import main.model.cliente.Cliente;
import main.model.pedido.Pedido;
import main.model.usuario.Perfil;
import main.model.usuario.Usuario;

public record UsuarioResponseDTO(
    Long id,
    String username,
    Perfil perfil,
    Cliente cliente,
    List<Long> pedidosIds) {

    public static UsuarioResponseDTO valueOf(Usuario usuario) {
        if (usuario == null)
            return null;

            List<Long> pedidosIds = usuario.getPedidos() == null
            ? List.of()
            : usuario.getPedidos().stream().map(Pedido::getId).toList();


        return new UsuarioResponseDTO(
            usuario.getId(), 
            usuario.getUsername(), 
            usuario.getPerfil(),
            usuario.getCliente(), 
            pedidosIds);
    }
    
}
