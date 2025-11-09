package main.service.plataforma;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import main.dto.plataformaDTO.PlataformaDTO;
import main.dto.plataformaDTO.PlataformaResponseDTO;
import main.model.controle.Plataforma;
import main.repository.PlataformaRepository;

@ApplicationScoped
public class PlataformaServiceImpl implements PlataformaService {

    @Inject
    PlataformaRepository plataformaRepository;

    @Override
    @Transactional
    public PlataformaResponseDTO create(PlataformaDTO dto) {
        Plataforma plataforma = new Plataforma();
        plataforma.setNome(dto.nome());
        plataformaRepository.persist(plataforma);
        return PlataformaResponseDTO.valueOf(plataforma);
    }

    @Override
    @Transactional
    public PlataformaResponseDTO update(Long id, PlataformaDTO dto) {
        Plataforma plataforma = plataformaRepository.findById(id);
        if (plataforma == null)
            throw new NotFoundException("Plataforma não encontrada com ID: " + id);

        plataforma.setNome(dto.nome());
        return PlataformaResponseDTO.valueOf(plataforma);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Plataforma plataforma = plataformaRepository.findById(id);
        if (plataforma == null)
            throw new NotFoundException("Plataforma não encontrada com ID: " + id);

        plataformaRepository.delete(plataforma);
    }

    @Override
    public PlataformaResponseDTO findById(Long id) {
        Plataforma plataforma = plataformaRepository.findById(id);
        if (plataforma == null)
            throw new NotFoundException("Plataforma não encontrada com ID: " + id);
        return PlataformaResponseDTO.valueOf(plataforma);
    }

    @Override
    public List<PlataformaResponseDTO> findAll(int page, int pageSize) {
        return plataformaRepository.findAllPagination()
                .page(page, pageSize)
                .list()
                .stream()
                .map(PlataformaResponseDTO::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    public Long count() {
        return plataformaRepository.count();
    }
}
