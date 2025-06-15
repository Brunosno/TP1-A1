package main.dto.plataformaDTO;

import main.model.controle.Plataforma;

public record PlataformaResponseDTO(Long id, String nome) {

    public static PlataformaResponseDTO valueOf(Plataforma plataforma) {
        return new PlataformaResponseDTO(plataforma.getId(), plataforma.getNome());
    }
}
