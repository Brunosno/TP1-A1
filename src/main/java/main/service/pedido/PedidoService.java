package main.service.pedido;

import main.dto.pedidoDTO.PedidoDTO;
import main.dto.pedidoDTO.PedidoResponseDTO;
import java.util.List;

public interface PedidoService {
    PedidoResponseDTO create(PedidoDTO dto);
    List<PedidoResponseDTO> findAll();
    PedidoResponseDTO findById(Long id);
    List<PedidoResponseDTO> findByUsuario(Long usuario_id);
    PedidoResponseDTO update(Long id, PedidoDTO dto);
    void delete(Long id);
}
