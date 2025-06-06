package main.service.controle;

import java.util.List;

import main.dto.controleDTO.ControleDTO;
import main.dto.controleDTO.ControleResponseDTO;

public interface ControleService {

    ControleResponseDTO create(ControleDTO controle);
    void update(long id, ControleDTO controle);
    void delete(long id);
    ControleResponseDTO findById(long id);
    List<ControleResponseDTO> findByFabricante(String fabricante);
    List<ControleResponseDTO> findByCor(String cor);
    List<ControleResponseDTO> findAll();
    
}
