package main.service.imagemControle;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

import main.dto.imagemControleDTO.ImagemControleDTO;
import main.dto.imagemControleDTO.ImagemControleResponseDTO;
import main.model.imagemControle.ImagemControle;
import main.repository.ImagemControleRepository;

@ApplicationScoped
public class ImagemControleServiceImpl implements ImagemControleService {

    @Inject
    ImagemControleRepository ImagemControleRepository;

    @Override
    @Transactional
    public ImagemControleResponseDTO create(ImagemControleDTO dto) {
        ImagemControle ImagemControle = new ImagemControle();
        ImagemControle.setUrl(dto.url());
        ImagemControle.setDescricao(dto.descricao());

        ImagemControleRepository.persist(ImagemControle);

        return ImagemControleResponseDTO.valueOf(ImagemControle);
    }

    @Override
    @Transactional
    public ImagemControleResponseDTO update(Long id, ImagemControleDTO dto) {
        ImagemControle ImagemControle = ImagemControleRepository.findById(id);
        if (ImagemControle == null) {
            throw new NotFoundException("Imagem de raquete não encontrada com ID: " + id);
        }

        ImagemControle.setUrl(dto.url());
        ImagemControle.setDescricao(dto.descricao());

        return ImagemControleResponseDTO.valueOf(ImagemControle);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        ImagemControle ImagemControle = ImagemControleRepository.findById(id);
        if (ImagemControle == null) {
            throw new NotFoundException("Imagem de raquete não encontrada com ID: " + id);
        }

        ImagemControleRepository.delete(ImagemControle);
    }

    @Override
    public ImagemControleResponseDTO findById(Long id) {
        ImagemControle ImagemControle = ImagemControleRepository.findById(id);
        if (ImagemControle == null) {
            throw new NotFoundException("Imagem de raquete não encontrada com ID: " + id);
        }

        return ImagemControleResponseDTO.valueOf(ImagemControle);
    }

    @Override
    public List<ImagemControleResponseDTO> findAll() {
        return ImagemControleRepository.listAll().stream()
                .map(ImagemControleResponseDTO::valueOf)
                .collect(Collectors.toList());
    }
}
