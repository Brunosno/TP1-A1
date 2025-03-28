package dto;

import model.Controle;
import model.Cor;
import java.util.List;
import model.Marca;

public record ControleResponseDTO(
    Long id,
    String nome,
    Marca marca,
    Cor cor) {

    public static ControleResponseDTO valueOf(Controle controle) {
        if (controle == null)
            return null;
        return new ControleResponseDTO(controle.getId(), controle.getNome(), controle.getMarca(), controle.getCor());
    }

    public static List<ControleResponseDTO> listOf(List<Controle> controles) {
        if (controles == null)
            return null;
        return controles.stream().map(ControleResponseDTO::valueOf).toList();
    }
    
}
