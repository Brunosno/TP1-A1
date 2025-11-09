package main.service.lote;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

import main.dto.loteDTO.LoteDTO;
import main.dto.loteDTO.LoteResponseDTO;
import main.model.lote.Lote;
import main.repository.LoteRepository;

@ApplicationScoped
public class LoteServiceImpl implements LoteService {

    @Inject
    LoteRepository loteRepository;

    @Override
    @Transactional
    public LoteResponseDTO create(LoteDTO dto) {
        Lote lote = new Lote();
        lote.setQuantidade(dto.quantidade());
        lote.setDescricao(dto.descricao());
        loteRepository.persist(lote);
        return LoteResponseDTO.valueOf(lote);
    }

    @Override
    @Transactional
    public LoteResponseDTO update(Long id, LoteDTO dto) {
        Lote lote = loteRepository.findById(id);
        if(lote == null) {
            throw new NotFoundException("Lote não encontrado com ID: " + id);
        }
        lote.setQuantidade(dto.quantidade());
        lote.setDescricao(dto.descricao());
        return LoteResponseDTO.valueOf(lote);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Lote lote = loteRepository.findById(id);
        if(lote == null) {
            throw new NotFoundException("Lote não encontrado com ID: " + id);
        }
        loteRepository.delete(lote);
    }

    @Override
    public LoteResponseDTO findById(Long id) {
        Lote lote = loteRepository.findById(id);
        if(lote == null) {
            throw new NotFoundException("Lote não encontrado com ID: " + id);
        }
        return LoteResponseDTO.valueOf(lote);
    }

    @Override
    public List<LoteResponseDTO> findAll() {
        return loteRepository.listAll().stream()
                .map(LoteResponseDTO::valueOf)
                .collect(Collectors.toList());
    }
}
