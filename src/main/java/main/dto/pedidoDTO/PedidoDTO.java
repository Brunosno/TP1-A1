package main.dto.pedidoDTO;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record PedidoDTO(
    @NotNull Long idCliente,
    @NotNull List<Long> idsControles
) {}