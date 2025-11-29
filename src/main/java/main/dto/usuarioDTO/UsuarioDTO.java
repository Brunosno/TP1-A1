package main.dto.usuarioDTO;

import main.dto.clienteDTO.ClienteDTO;

public record UsuarioDTO(
    String username,
    String senha,
    Integer idPerfil,
    ClienteDTO cliente) {}
