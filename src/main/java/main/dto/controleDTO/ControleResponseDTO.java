package main.dto.controleDTO;

import java.util.List;
import java.util.stream.Collectors;

import main.dto.plataformaDTO.PlataformaResponseDTO;
import main.model.controle.Controle;
import main.model.controle.Cor;
import main.model.fabricante.Fabricante;
import main.model.imagemControle.ImagemControle;
import main.model.lote.Lote;

public record ControleResponseDTO(
    Long id,
    String nome,
    Double preco,
    Integer estoque,
    Fabricante fabricante,
    Cor cor,
    List<PlataformaResponseDTO> plataformas,
    String conexao,
    String alimentacao,
    boolean touchpad,
    boolean gatilhosAdaptaveis,
    List<Long> lotes,
    List<ImagemControleResponseDTO> imagens
) {

    public static ControleResponseDTO valueOf(Controle controle) {
        return new ControleResponseDTO(
            controle.getId(),
            controle.getNome(),
            controle.getPreco(),
            controle.getEstoque(),
            controle.getFabricante(),
            controle.getCor(),
            controle.getPlataformas() != null
                ? controle.getPlataformas().stream().map(PlataformaResponseDTO::valueOf).toList()
                : null,
            controle.getConexao(),
            controle.getAlimentacao(),
            controle.isTouchpad(),
            controle.isGatilhosAdaptaveis(),
            controle.getLotes() != null
                ? controle.getLotes().stream().map(Lote::getId).collect(Collectors.toList())
                : null,
            controle.getImagens() != null
                ? controle.getImagens().stream().map(ImagemControleResponseDTO::valueOf).collect(Collectors.toList())
                : null
        );
    }

    public record ImagemControleResponseDTO(Long id, String url, String descricao) {
        public static ImagemControleResponseDTO valueOf(ImagemControle img) {
            return new ImagemControleResponseDTO(img.getId(), img.getUrl(), img.getDescricao());
        }
    }

    public static List<ControleResponseDTO> listOf(List<Controle> controles) {
        if (controles == null)
            return null;
        return controles.stream().map(ControleResponseDTO::valueOf).toList();
    }
}

