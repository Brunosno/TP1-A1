package main.dto.usuarioDTO;

public record UsuarioDTO(
    String username,
    String senha,
    Integer idPerfil,
    Long idCliente) {}
