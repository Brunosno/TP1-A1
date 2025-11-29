package main.service.pedido;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.time.LocalDate;

import main.dto.pedidoDTO.ItemPedidoDTO;
import main.dto.pedidoDTO.PedidoDTO;
import main.dto.pedidoDTO.PedidoResponseDTO;
import main.model.pedido.ItemPedido;
import main.model.pedido.Pedido;
import main.model.usuario.Usuario;
import main.model.endereco.Endereco;
import main.model.controle.Controle;
import main.model.pagamento.TipoPagamento;
import main.repository.ControleRepository;
import main.repository.EnderecoRepository;
import main.repository.PedidoRepository;
import main.repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class PedidoServiceImpl implements PedidoService {

    @Inject
    PedidoRepository pedidoRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    ControleRepository controleRepository;

    @Inject
    EnderecoRepository enderecoRepository;

    @Override
    @Transactional
    public PedidoResponseDTO create(PedidoDTO pedidoDTO){

        Usuario usuario = usuarioRepository.findById(pedidoDTO.idUsuario());

        Endereco endereco = enderecoRepository.findById(pedidoDTO.idEndereco());

        double totalCalculado = 0;

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setDataPedido(LocalDate.now());
        pedido.setTipo_pagamento(TipoPagamento.valueOf(pedidoDTO.idPagamento()));
        pedido.setEndereco(endereco);

        List<ItemPedido> listaItem = new ArrayList<ItemPedido>();
        for (ItemPedidoDTO  itemDTO : pedidoDTO.itens()) {
            Controle controle = controleRepository.findById(itemDTO.idProduto());

            ItemPedido item = new ItemPedido();
            item.setPedido(pedido);
            item.setControle(controle);
            item.setPreco(item.getControle().getPreco());
            item.setQuantidade(itemDTO.quantidade());

            double subtotal = item.getPreco() * item.getQuantidade();
            totalCalculado += subtotal;

            listaItem.add(item);

            controle.setEstoque(controle.getEstoque() - itemDTO.quantidade());

        }

        pedido.setItens(listaItem);
        pedido.setTotal(totalCalculado);

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

        Usuario usuario = usuarioRepository.findById(dto.idUsuario());
        Endereco endereco = enderecoRepository.findById(dto.idEndereco());
        double totalCalculado = 0;

        pedido.setUsuario(usuario);
        pedido.setDataPedido(LocalDate.now());
        pedido.setTipo_pagamento(TipoPagamento.valueOf(dto.idPagamento()));
        pedido.setEndereco(endereco);

        List<ItemPedido> listaItem = new ArrayList<ItemPedido>();
        for (ItemPedidoDTO  itemDTO : dto.itens()) {
            Controle controle = controleRepository.findById(itemDTO.idProduto());

            ItemPedido item = new ItemPedido();
            item.setPedido(pedido);
            item.setControle(controle);
            item.setPreco(item.getControle().getPreco());
            item.setQuantidade(itemDTO.quantidade());
            double subtotal = item.getPreco() * item.getQuantidade();
            totalCalculado += subtotal;

            listaItem.add(item);

            controle.setEstoque(controle.getEstoque() - itemDTO.quantidade());
        }

        pedido.setItens(listaItem);
        pedido.setTotal(totalCalculado);

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
    public List<PedidoResponseDTO> findByUsuario(Long usuario_id) {
        List<Pedido> pedidos = pedidoRepository.findByUsuario(usuario_id);
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
