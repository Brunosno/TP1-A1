package main.dto.pedidoDTO;

public record ItemPedidoDTO(
    Long idProduto,
    Double preco,
    Integer quantidade
) {
    
}
