package main.service.usuario;

import main.dto.clienteDTO.ClienteResponseDTO;
import main.dto.usuarioDTO.UsuarioDTO;
import main.dto.usuarioDTO.UsuarioResponseDTO;
import main.model.cliente.Cliente;
import main.model.usuario.Perfil;
import main.model.usuario.Usuario;
import main.repository.ClienteRepository;
import main.repository.UsuarioRepository;
import main.service.cliente.ClienteService;
import main.service.hashpassword.HashService;

import java.util.stream.Collectors;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    HashService hashSenha;

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    ClienteService clienteService;

    @Override
    public UsuarioResponseDTO create(UsuarioDTO usuario) throws Exception {
        try {
            Usuario newUsuario = new Usuario();

            ClienteResponseDTO clienteNovo = clienteService.create(usuario.cliente());
            if (clienteNovo == null) {
                throw new IllegalArgumentException("Falha ao criar cliente para o usuário.");
            }

            Cliente cliente = clienteRepository.findById(clienteNovo.id());
            if (cliente == null) {
                throw new IllegalArgumentException("Cliente não encontrado com ID: " + clienteNovo.id());
            }

            if (usuario.idPerfil() == null || Perfil.valueOf(usuario.idPerfil()) == null) {
                newUsuario.setPerfil(Perfil.valueOf(2));
            } else {
                newUsuario.setPerfil(Perfil.valueOf(usuario.idPerfil()));
            }

            newUsuario.setUsername(usuario.username());
            newUsuario.setSenha(hashSenha.getHashSenha(usuario.senha()));
            newUsuario.setCliente(cliente);

            usuarioRepository.persist(newUsuario);

            return UsuarioResponseDTO.valueOf(newUsuario);

        } catch (Exception e) {
            System.err.println("Erro ao criar usuário: " + e.getMessage());
            e.printStackTrace();
            throw new Exception("Falha ao criar usuário.", e);
        }
    }

    @Override
    public UsuarioResponseDTO findByUsernameAndSenha(String username, String senha) {
        try {
            Usuario usuario = usuarioRepository.findByUsernameAndSenha(username, senha);
            if (usuario == null) return null;
            return UsuarioResponseDTO.valueOf(usuario);
        } catch (Exception e) {
            System.err.println("Erro ao buscar usuário por username e senha: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar usuário por credenciais.", e);
        }
    }

    @Override
    public UsuarioResponseDTO findByUsername(String username) {
        try {
            Usuario usuario = usuarioRepository.findByUsername(username);
            if (usuario == null) return null;
            return UsuarioResponseDTO.valueOf(usuario);
        } catch (Exception e) {
            System.err.println("Erro ao buscar usuário por username: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar usuário pelo username.", e);
        }
    }

    @Override
    public UsuarioResponseDTO findById(long id) {
        try {
            Usuario usuario = usuarioRepository.findById(id);
            return usuario != null ? UsuarioResponseDTO.valueOf(usuario) : null;
        } catch (Exception e) {
            System.err.println("Erro ao buscar usuário por ID: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar usuário por ID.", e);
        }
    }

    @Override
    public List<UsuarioResponseDTO> findAll() {
        try {
            return usuarioRepository.listAll()
                    .stream()
                    .map(UsuarioResponseDTO::valueOf)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Erro ao listar todos os usuários: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao listar todos os usuários.", e);
        }
    }

    @Override
    public UsuarioResponseDTO update(long id, UsuarioDTO dto) throws Exception {
        try {
            Usuario usuario = usuarioRepository.findById(id);
            if (usuario == null) {
                throw new IllegalArgumentException("Usuário não encontrado com ID: " + id);
            }
            ClienteResponseDTO clienteAntigo = clienteService.findById(usuario.getCliente().getId());
            if (clienteAntigo == null) {
                throw new IllegalArgumentException("Falha ao encontrar cliente do usuário.");
            }

            clienteService.update(clienteAntigo.id(), dto.cliente());

            Cliente cliente = clienteRepository.findById(clienteAntigo.id());
            if (cliente == null) {
                throw new IllegalArgumentException("Cliente não encontrado com ID: " + clienteAntigo.id());
            }

            usuario.setUsername(dto.username());

            String hashSenhaNova = hashSenha.getHashSenha(dto.senha());

            if(hashSenhaNova != usuario.getSenha()){
                usuario.setSenha(hashSenha.getHashSenha(dto.senha()));
            }

            usuario.setPerfil(Perfil.valueOf(usuario.getPerfil().getId()));
            usuario.setCliente(cliente);
        } catch (Exception e) {
            System.err.println("Erro ao atualizar usuário: " + e.getMessage());
            e.printStackTrace();
            throw new Exception("Falha ao atualizar usuário.", e);
        }

        return UsuarioResponseDTO.valueOf(usuarioRepository.findById(id));
    }

    @Override
    public void delete(long id) {
        try {
            Usuario usuario = usuarioRepository.findById(id);
            if (usuario == null) {
                throw new IllegalArgumentException("Usuário não encontrado com ID: " + id);
            }

            Cliente cliente = clienteRepository.findById(usuario.getCliente().getId());
            if (cliente == null) {
                throw new IllegalArgumentException("Cliente não encontrado com ID: " + usuario.getCliente().getId());
            }
            clienteRepository.delete(cliente);
            usuarioRepository.delete(usuario);
        } catch (Exception e) {
            System.err.println("Erro ao deletar usuário: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Falha ao deletar usuário.", e);
        }
    }
}

