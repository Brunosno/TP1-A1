package dto;

import model.Controle;
import model.Cor;

public record ControleResponseDTO(
    Long id,
    String nome,
    String sigla,
    Cor Cor) {

    public static ControleResponseDTO valueOf(Controle controle) {
        if (controle == null)
            return null;
        return new ControleResponseDTO(controle.getId(), controle.getNome(), controle.getMarca(), controle.getCor());
    }
    
}
