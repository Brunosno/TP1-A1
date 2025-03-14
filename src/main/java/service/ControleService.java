package service;

import java.util.List;

import dto.ControleDTO;
import dto.ControleResponseDTO;

public interface ControleService {

    ControleResponseDTO create(ControleDTO controle);
    void update(long id, ControleDTO controle);
    void delete(long id);
    ControleResponseDTO findById(long id);
    ControleResponseDTO findByMarca(String marca);
    List<ControleResponseDTO> findAll();
    
}
