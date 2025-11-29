package main.service.fabricante;

import java.util.List;

import main.dto.fabricanteDTO.FabricanteDTO;
import main.dto.fabricanteDTO.FabricanteResponseDTO;

public interface FabricanteService {
    FabricanteResponseDTO create(FabricanteDTO fabricante);
    void update(Long id, FabricanteDTO fabricante);
    void delete(Long id);
    List<FabricanteResponseDTO> findAll();
    FabricanteResponseDTO findById(Long id);
    FabricanteResponseDTO findByCNPJ(String cnpj);
}
