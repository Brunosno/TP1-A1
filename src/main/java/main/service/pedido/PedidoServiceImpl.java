package main.service.pedido;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.time.LocalDate;
import main.dto.pedidoDTO.PedidoDTO;
import main.dto.pedidoDTO.PedidoResponseDTO;
import main.model.pedido.Pedido;
import main.model.usuario.Usuario;
import main.model.controle.Controle;
import main.model.pagamento.TipoPagamento;
import main.repository.ControleRepository;
import main.repository.PedidoRepository;
import main.repository.UsuarioRepository;

import java.util.List;

@ApplicationScoped
public class PedidoServiceImpl implements PedidoService {

    @Inject
    PedidoRepository pedidoRepository;

    @Inject
    UsuarioRepository clienteRepository;

    @Inject
    ControleRepository controleRepository;

    @Override
    @Transactional
    public PedidoResponseDTO create(PedidoDTO pedidoDTO) {

        Usuario cliente = clienteRepository.findById(pedidoDTO.idCliente());
        
        List<Controle> controles = pedidoDTO.idsControles().stream()
            .map(controleRepository::findById)
            .toList();

        Integer preco = controles.stream().mapToInt(Controle::getPreco).sum();

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setControles(controles);
        pedido.setPreco(preco);
        pedido.setDataPedido(LocalDate.now());
        pedido.setTipo_pagamento(TipoPagamento.valueOf(pedidoDTO.idPagamento()));

        pedidoRepository.persist(pedido);

        return PedidoResponseDTO.valueOf(pedido);
    }

    @Override
    @Transactional
    public PedidoResponseDTO update(Long id, PedidoDTO dto) {
        Pedido pedido = pedidoRepository.findById(id);
        if (pedido == null) {
            throw new NotFoundException("Pedido não encontrado com ID: " + id);
        }

        Usuario cliente = clienteRepository.findById(dto.idCliente());
        List<Controle> controles = dto.idsControles().stream()
            .map(controleRepository::findById)
            .toList();

        Integer preco = controles.stream().mapToInt(Controle::getPreco).sum();

        pedido.setCliente(cliente);
        pedido.setControles(controles);
        pedido.setPreco(preco);
        pedido.setTipo_pagamento(TipoPagamento.valueOf(dto.idPagamento()));

        return PedidoResponseDTO.valueOf(pedido);
    }

    @Override
    public List<PedidoResponseDTO> findAll() {
        return pedidoRepository.listAll().stream()
            .map(PedidoResponseDTO::valueOf)
            .toList();
    }

    @Override
    public PedidoResponseDTO findById(Long id) {
        Pedido pedido = pedidoRepository.findById(id);
        if (pedido == null) {
            throw new NotFoundException("Pedido não encontrado com ID: " + id);
        }
        return PedidoResponseDTO.valueOf(pedido);
    }

    @Override
    public List<PedidoResponseDTO> findByClienteId(Long clienteId) {
        List<Pedido> pedidos = pedidoRepository.findByClienteId(clienteId);
        return pedidos.stream()
                .map(PedidoResponseDTO::valueOf)
                .toList();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Pedido pedido = pedidoRepository.findById(id);
        if (pedido == null) {
            throw new NotFoundException("Pedido não encontrado com ID: " + id);
        }
        pedidoRepository.delete(pedido);
    }
}
