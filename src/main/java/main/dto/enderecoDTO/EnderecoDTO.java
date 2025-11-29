package main.dto.enderecoDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import main.model.endereco.Endereco;

public record EnderecoDTO(
    @NotBlank(message = "A rua é obrigatória")
    String rua,

    @NotBlank(message = "O número é obrigatório")
    String numero,

    @NotBlank(message = "O bairro é obrigatório")
    String bairro,

    @NotBlank(message = "A cidade é obrigatória")
    String cidade,

    @NotBlank(message = "O estado é obrigatório")
    @Pattern(
        regexp = "^(AC|AL|AP|AM|BA|CE|DF|ES|GO|MA|MT|MS|MG|PA|PB|PR|PE|PI|RJ|RN|RS|RO|RR|SC|SP|SE|TO)$",
        message = "O estado deve ser uma sigla válida (ex: TO, SP, RJ)"
    )
    String estado,

    @NotBlank(message = "O CEP é obrigatório")
    @Pattern(
        regexp = "^\\d{5}-?\\d{3}$",
        message = "O CEP deve conter 8 dígitos numéricos, com ou sem hífen (ex: 77001-234 ou 77001234)"
    )
    String cep
) {
    public static EnderecoDTO valueOf(Endereco endereco) {
        if (endereco == null) return null;

        return new EnderecoDTO(
            endereco.getRua(),
            endereco.getNumero(),
            endereco.getBairro(),
            endereco.getCidade(),
            endereco.getEstado(),
            endereco.getCep()
        );
    }
}
