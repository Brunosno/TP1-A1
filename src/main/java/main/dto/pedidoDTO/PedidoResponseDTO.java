package main.dto.pedidoDTO;

import java.time.LocalDate;
import java.util.List;

import main.model.endereco.Endereco;
import main.model.pagamento.TipoPagamento;
import main.model.pedido.Pedido;

public record PedidoResponseDTO(
    Long id,
    String usuario,
    Long id_usuario,
    List<ItemPedidoResponseDTO> lista,
    Double total,
    LocalDate data,
    TipoPagamento pagamento,
    Endereco endereco
) {
    public static PedidoResponseDTO valueOf(Pedido pedido) {
        if (pedido == null)
            return null;

        String usuario = pedido.getUsuario() != null ? pedido.getUsuario().getUsername() : null;
        Long id_usuario = pedido.getUsuario() != null ? pedido.getUsuario().getId() : null;

        return new PedidoResponseDTO(
            pedido.getId(),
            usuario,
            id_usuario,
            pedido.getItens().stream().map(i -> ItemPedidoResponseDTO.valueOf(i)).toList(),
            pedido.getTotal(),
            pedido.getDataPedido(),
            pedido.getTipo_pagamento(),
            pedido.getEndereco()
        );
    }

    public static List<PedidoResponseDTO> listOf(List<Pedido> pedidos) {
        if (pedidos == null)
            return null;
        return pedidos.stream().map(PedidoResponseDTO::valueOf).toList();
    }
}
