package main.dto.pedidoDTO;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PedidoDTO(
    @NotNull(message = "O ID do cliente é obrigatório") Long idUsuario,
    @NotNull(message = "O(s) ID(s) do(s) item(s) é obrigatório")List<ItemPedidoDTO> itens,
    @NotNull(message = "O ID do pagamento é obrigatório") Integer idPagamento,
    @NotNull(message = "O ID do endereço é obrigatório") Long idEndereco,
    Double total
) {}