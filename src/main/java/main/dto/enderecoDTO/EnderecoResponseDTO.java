package main.dto.enderecoDTO;

import main.model.cliente.Endereco;

public record EnderecoResponseDTO(
    Long id,
    String rua,
    String numero,
    String bairro,
    String cidade,
    String estado,
    String cep
) {
    public static EnderecoResponseDTO valueOf(Endereco endereco) {
        if (endereco == null) return null;

        return new EnderecoResponseDTO(
            endereco.getId(),
            endereco.getRua(),
            endereco.getNumero(),
            endereco.getBairro(),
            endereco.getCidade(),
            endereco.getEstado(),
            endereco.getCep()
        );
    }
}
