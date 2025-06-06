package main.service.cliente;

import java.util.List;

import main.dto.clienteDTO.ClienteDTO;
import main.dto.clienteDTO.ClienteResponseDTO;

public interface ClienteService {

    ClienteResponseDTO create(ClienteDTO cliente);
    void update(Long id, ClienteDTO cliente);
    void delete(Long id);
    List<ClienteResponseDTO> findAll();
    ClienteResponseDTO findById(Long id);
    ClienteResponseDTO findByCPF(String cpf);
}
