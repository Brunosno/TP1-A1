package main.dto.pedidoDTO;

import main.model.pedido.ItemPedido;

public record ItemPedidoResponseDTO(
    Long idProduto,
    Integer quantidade,
    Double preco) {

    public static ItemPedidoResponseDTO valueOf(ItemPedido itemPedido) {
        return new ItemPedidoResponseDTO (
            itemPedido.getControle().getId(), 
            itemPedido.getQuantidade(),
            itemPedido.getPreco());
    }
}