package main.dto.pedidoDTO;

import java.time.LocalDate;
import java.util.List;

import main.dto.controleDTO.ControleDTO;
import main.model.pagamento.TipoPagamento;
import main.model.pedido.Pedido;

public record PedidoResponseDTO(
    Long id,
    String usuario,
    Long id_usuario,
    List<ControleDTO> controles,
    Integer preco,
    LocalDate data,
    TipoPagamento pagamento
    
) {
    public static PedidoResponseDTO valueOf(Pedido pedido) {
        if (pedido == null)
            return null;

        String cliente = pedido.getCliente() != null ? pedido.getCliente().getUsername() : null;
        Long id_cliente = pedido.getCliente() != null ? pedido.getCliente().getId() : null;

        List<ControleDTO> controlesInfo = pedido.getControles() != null
            ? pedido.getControles().stream().map(ControleDTO::valueOf).toList()
            : List.of();

        return new PedidoResponseDTO(
            pedido.getId(),
            cliente,
            id_cliente,
            controlesInfo,
            pedido.getPreco(),
            pedido.getDataPedido(),
            pedido.getTipo_pagamento()
        );
    }

    public static List<PedidoResponseDTO> listOf(List<Pedido> pedidos) {
        if (pedidos == null)
            return null;
        return pedidos.stream().map(PedidoResponseDTO::valueOf).toList();
    }
}
