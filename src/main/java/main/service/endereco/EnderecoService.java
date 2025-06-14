package main.service.endereco;

import java.util.List;

import main.dto.enderecoDTO.EnderecoDTO;
import main.dto.enderecoDTO.EnderecoResponseDTO;

public interface EnderecoService {
    EnderecoResponseDTO create(EnderecoDTO dto);
    void update(Long id, EnderecoDTO dto);
    void delete(Long id);
    EnderecoResponseDTO findByCEP(String cep);
    EnderecoResponseDTO findById(Long id);
    List<EnderecoResponseDTO> findAll();
}