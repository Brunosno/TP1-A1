package main.service.usuario;

import main.dto.usuarioDTO.UsuarioDTO;
import main.dto.usuarioDTO.UsuarioResponseDTO;

import java.util.List;

public interface UsuarioService {

    UsuarioResponseDTO create(UsuarioDTO usuario) throws Exception;
    void update(long id, UsuarioDTO usuario) throws Exception;
    void delete(long id);
    UsuarioResponseDTO findById(long id);
    List<UsuarioResponseDTO> findAll();
    UsuarioResponseDTO findByUsernameAndSenha(String username, String senha);
    UsuarioResponseDTO findByUsername(String username);

    
}
