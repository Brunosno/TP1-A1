package main.dto.controleDTO;

import java.util.List;

import main.model.controle.Controle;
import main.model.controle.Cor;
import main.model.controle.Fabricante;

public record ControleResponseDTO(
    Long id,
    String nome,
    Fabricante fabricante,
    Cor cor,
    Double preco,
    String conexao,
    String alimentacao,
    boolean touchpad,
    boolean gatilhosAdaptaveis,
    Integer estoque) {

    public static ControleResponseDTO valueOf(Controle controle) {
        if (controle == null)
            return null;
        return new ControleResponseDTO(
            controle.getId(), 
            controle.getNome(), 
            controle.getFabricante(), 
            controle.getCor(), 
            controle.getPreco(),
            controle.getConexao(), 
            controle.getAlimentacao(), 
            controle.isTouchpad(), 
            controle.isGatilhosAdaptaveis(),
            controle.getEstoque()
        );
    }

    public static List<ControleResponseDTO> listOf(List<Controle> controles) {
        if (controles == null)
            return null;
        return controles.stream().map(ControleResponseDTO::valueOf).toList();
    }
    
}
