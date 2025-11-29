package main.service.imagemControle;

import java.util.List;

import main.dto.imagemControleDTO.ImagemControleDTO;
import main.dto.imagemControleDTO.ImagemControleResponseDTO;

public interface ImagemControleService {
    ImagemControleResponseDTO create(ImagemControleDTO dto);

    ImagemControleResponseDTO update(Long id, ImagemControleDTO dto);

    void delete(Long id);

    ImagemControleResponseDTO findById(Long id);

    List<ImagemControleResponseDTO> findAll();
}
