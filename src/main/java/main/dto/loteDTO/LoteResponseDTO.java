package main.dto.loteDTO;

import main.model.lote.Lote;

public record LoteResponseDTO(
    Long id,
    Integer quantidade,
    String descricao
) {
    public static LoteResponseDTO valueOf(Lote lote){
        return new LoteResponseDTO(lote.getId(), lote.getQuantidade(), lote.getDescricao());
    }
}
