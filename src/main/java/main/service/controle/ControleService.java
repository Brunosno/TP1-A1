package main.service.controle;

import java.util.List;

import org.jboss.resteasy.reactive.multipart.FileUpload;

import main.dto.controleDTO.ControleDTO;
import main.dto.controleDTO.ControleResponseDTO;

public interface ControleService {

    ControleResponseDTO create(ControleDTO controle);
    ControleResponseDTO update(long id, ControleDTO controle);
    void delete(long id);
    ControleResponseDTO findById(long id);
    List<ControleResponseDTO> findByFabricante(String fabricante, int page, int pageSize);
    List<ControleResponseDTO> findByCor(String cor);
    List<ControleResponseDTO> findAll(int page, int pageSize);
    Long count();
    ControleResponseDTO createImage(ControleDTO dto, List<FileUpload> files);
    ControleResponseDTO updateImage(long id, ControleDTO dto, List<FileUpload> files);
}
