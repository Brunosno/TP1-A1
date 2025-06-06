package main.dto.clienteDTO;

import java.util.List;
import main.model.cliente.Cliente;
import main.model.cliente.Telefone;
import main.model.pedido.Pedido;
import main.dto.enderecoDTO.EnderecoDTO;

public record ClienteResponseDTO(
    Long id,
    String nome,
    String cpf,
    String email,
    Telefone telefone,
    List<Long> pedidosIds,
    List<EnderecoDTO> enderecos
) {
    public static ClienteResponseDTO valueOf(Cliente cliente) {
        if (cliente == null)
            return null;

        List<Long> pedidosIds = cliente.getPedidos() == null
            ? List.of()
            : cliente.getPedidos().stream().map(Pedido::getId).toList();

        List<EnderecoDTO> enderecosDTO = cliente.getEnderecos() == null
            ? List.of()
            : cliente.getEnderecos().stream().map(EnderecoDTO::valueOf).toList();

        return new ClienteResponseDTO(
            cliente.getId(), 
            cliente.getNome(), 
            cliente.getCpf(), 
            cliente.getEmail(), 
            cliente.getTelefone(), 
            pedidosIds,
            enderecosDTO
        );
    }

    public static List<ClienteResponseDTO> listOf(List<Cliente> clientes) {
        if (clientes == null)
            return null;
        return clientes.stream().map(ClienteResponseDTO::valueOf).toList();
    }
}
