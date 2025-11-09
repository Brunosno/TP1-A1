package main.service.lote;

import java.util.List;

import main.dto.loteDTO.LoteDTO;
import main.dto.loteDTO.LoteResponseDTO;

public interface LoteService {
    LoteResponseDTO create(LoteDTO dto);
    LoteResponseDTO update(Long id, LoteDTO dto);
    void delete(Long id);
    LoteResponseDTO findById(Long id);
    List<LoteResponseDTO> findAll();
}
