package main.dto.telefoneDTO;

import main.model.telefone.Telefone;

public record TelefoneResponseDTO(
    Long id,
    String numero
) {
    public static TelefoneResponseDTO valueOf(Telefone telefone){
        if (telefone == null)
            return null;
        return new TelefoneResponseDTO(telefone.getId(), telefone.getNumero());
    }
}
