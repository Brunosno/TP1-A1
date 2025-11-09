package main.dto.controleDTO;

import java.util.List;

import main.dto.plataformaDTO.PlataformaResponseDTO;
import main.model.controle.Controle;
import main.model.controle.Cor;
import main.model.controle.Fabricante;
import main.model.lote.Lote;

public record ControleResponseDTO(
    Long id,
    String nome,
    Fabricante fabricante,
    List<PlataformaResponseDTO> plataformas,
    Cor cor,
    Double preco,
    String conexao,
    String alimentacao,
    boolean touchpad,
    boolean gatilhosAdaptaveis,
    List<Lote> lotes,
    Integer estoque) {

    public static ControleResponseDTO valueOf(Controle controle) {
        if (controle == null)
            return null;

        return new ControleResponseDTO(
            controle.getId(), 
            controle.getNome(), 
            controle.getFabricante(),
            controle.getPlataformas() != null
            ? controle.getPlataformas().stream().map(PlataformaResponseDTO::valueOf).toList()
            : null,
            controle.getCor(), 
            controle.getPreco(),
            controle.getConexao(), 
            controle.getAlimentacao(), 
            controle.isTouchpad(), 
            controle.isGatilhosAdaptaveis(),
            controle.getLotes() != null ? controle.getLotes() : null,
            controle.getEstoque()
        );
    }

    public static List<ControleResponseDTO> listOf(List<Controle> controles) {
        if (controles == null)
            return null;
        return controles.stream().map(ControleResponseDTO::valueOf).toList();
    }
    
}
