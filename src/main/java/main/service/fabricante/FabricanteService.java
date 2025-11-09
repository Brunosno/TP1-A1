package main.service.fabricante;

import java.util.List;
import main.dto.fabricanteDTO.FabricanteDTO;
import main.dto.fabricanteDTO.FabricanteResponseDTO;

public interface FabricanteService {

    FabricanteResponseDTO create(FabricanteDTO fabricanteDTO);
    void update(Long id, FabricanteDTO fabricanteDTO);
    void delete(Long id);

    List<FabricanteResponseDTO> findAll(int page, int pageSize); // ✅ novo
    FabricanteResponseDTO findById(Long id);
    FabricanteResponseDTO findByCNPJ(String cnpj);

    Long count(); // ✅ novo
}
