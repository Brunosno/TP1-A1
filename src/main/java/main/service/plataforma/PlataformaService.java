package main.service.plataforma;

import java.util.List;
import main.dto.plataformaDTO.PlataformaDTO;
import main.dto.plataformaDTO.PlataformaResponseDTO;

public interface PlataformaService {
    PlataformaResponseDTO create(PlataformaDTO dto);
    PlataformaResponseDTO update(Long id, PlataformaDTO dto);
    void delete(Long id);
    PlataformaResponseDTO findById(Long id);
    List<PlataformaResponseDTO> findAll(int page, int pageSize);
    Long count();
}
