package main.dto.controleDTO;

import java.util.List;

public record ControleDTO(
    String nome,
    Double preco,
    Long idFabricante,
    Integer idCor,
    List<Long> idsPlataformas,
    String conexao,
    String alimentacao,
    boolean touchpad,
    boolean gatilhosAdaptaveis,
    List<Long> loteIds,
    List<ImagemControleDTO> imagens
) {
    public record ImagemControleDTO(String url, String descricao) {}
}
