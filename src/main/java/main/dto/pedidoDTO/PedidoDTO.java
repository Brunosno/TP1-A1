package main.dto.pedidoDTO;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PedidoDTO(
    @NotNull(message = "O ID do cliente é obrigatório") Long idCliente,
    @NotNull(message = "O ID do(s) controle(s) é obrigatório")List<Long> idsControles,
    @NotNull(message = "O ID do pagamento é obrigatório") Integer idPagamento
) {}