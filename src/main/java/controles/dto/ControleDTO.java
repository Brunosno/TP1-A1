package controles.dto;

import controles.model.Marca;

public record ControleDTO(
    String nome,
    Marca marca,
    Integer idCor) {
    
}
