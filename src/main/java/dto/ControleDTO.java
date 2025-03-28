package dto;

import model.Marca;

public record ControleDTO(
    String nome,
    Marca marca,
    Integer idCor) {
    
}
