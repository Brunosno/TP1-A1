package main.dto.fabricanteDTO;

import java.util.List;

import main.model.telefone.Telefone;
import main.model.fabricante.Fabricante;
import main.dto.enderecoDTO.EnderecoDTO;

public record FabricanteResponseDTO(
    Long id,
    String nome,
    String cnpj,
    String email,
    Telefone telefone,
    List<EnderecoDTO> enderecos
) {
    public static FabricanteResponseDTO valueOf(Fabricante fabricante) {
        if (fabricante == null)
            return null;

        List<EnderecoDTO> enderecosDTO = fabricante.getEnderecos() == null
            ? List.of()
            : fabricante.getEnderecos().stream().map(EnderecoDTO::valueOf).toList();

        return new FabricanteResponseDTO(
            fabricante.getId(),
            fabricante.getNome(),
            fabricante.getCNPJ(),
            fabricante.getEmail(),
            fabricante.getTelefone(),
            enderecosDTO
        );
    }

    public static List<FabricanteResponseDTO> listOf(List<Fabricante> fabricantes) {
        if (fabricantes == null)
            return null;
        return fabricantes.stream().map(FabricanteResponseDTO::valueOf).toList();
    }
}