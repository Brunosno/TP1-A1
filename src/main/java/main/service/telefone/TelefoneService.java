package main.service.telefone;

import java.util.List;

import main.dto.telefoneDTO.TelefoneDTO;
import main.dto.telefoneDTO.TelefoneResponseDTO;

public interface TelefoneService {
    
    TelefoneResponseDTO create(TelefoneDTO telefone);
    void update(Long id, TelefoneDTO telefone);
    void delete(Long id);
    TelefoneResponseDTO findById(long id);
    TelefoneResponseDTO findByNumber(String numero);
    List<TelefoneResponseDTO> findAll();
}
