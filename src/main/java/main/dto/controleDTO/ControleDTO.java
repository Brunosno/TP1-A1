package main.dto.controleDTO;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import main.model.controle.Controle;

public record ControleDTO(
    @NotBlank(message = "O nome é obrigatório")
    String nome,

    @NotNull(message = "O ID do fabricante é obrigatório")
    @Positive(message = "O ID do fabricante deve ser positivo")
    Long idFabricante,

    List<Long> idsPlataformas,

    @NotNull(message = "O ID da cor é obrigatório")
    @Positive(message = "O ID da cor deve ser positivo")
    Integer idCor,

    @NotNull(message = "O preço é obrigatório")
    @Positive(message = "O preço deve ser positivo")
    Double preco,

    @NotBlank(message = "O tipo de conexão é obrigatório")
    String conexao,

    @NotBlank(message = "O tipo de alimentação é obrigatório")
    String alimentacao,

    boolean touchpad,

    boolean gatilhosAdaptaveis,
    
    Integer quantidade) {

        public static ControleDTO valueOf(Controle controle) {
        if (controle == null) return null;

        return new ControleDTO(
            controle.getNome(),
            controle.getFabricante() != null ? controle.getFabricante().getId() : null,
            controle.getPlataformas() != null ? controle.getPlataformas().stream().map(p -> p.getId()).toList() : null,
            controle.getCor() != null ? controle.getCor().getId() : null,
            controle.getPreco(),
            controle.getConexao(),
            controle.getAlimentacao(),
            controle.isTouchpad(),
            controle.isGatilhosAdaptaveis(),
            controle.getEstoque()
        );
    }
}
