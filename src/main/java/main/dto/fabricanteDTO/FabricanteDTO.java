package main.dto.fabricanteDTO;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record FabricanteDTO(

    @NotBlank(message = "O nome deve ser informado.")
    @Size(max = 60, message = "O nome deve possuir no máximo 60 caracteres.")
    String nome,

    @Size(max = 18, min = 14, message = "O cnpj deve possuir 14 caracteres.")
    @Pattern(regexp = "\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}\\-\\d{2}", message = "Formato de CNPJ inválido. Use XX.XXX.XXX/XXXX-XX")
    @NotNull(message = "O cnpj não pode ser nulo.")
    String cnpj,
    
    @NotBlank(message = "O nome deve ser informado.")
    @Size(max = 30, min = 10, message = "O email deve possuir no mínimo 10 caracteres.")
    String email,

    Long idtelefone,

    List<Long> idEnderecos
){
    
}