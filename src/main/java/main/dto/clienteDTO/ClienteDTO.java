package main.dto.clienteDTO;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import main.dto.telefoneDTO.TelefoneDTO;

public record ClienteDTO(

    @NotBlank(message = "O nome deve ser informado.")
    @Size(max = 60, message = "O nome deve possuir no máximo 60 caracteres.")
    String nome,

    @Size(max = 18, min = 14, message = "O cpf deve possuir 11 caracteres.")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}", message = "Formato de CPF inválido. Use XXX.XXX.XXX-XX")
    @NotNull(message = "O CPF não pode ser nulo.")
    String cpf,
    
    @NotBlank(message = "O nome deve ser informado.")
    @Size(max = 30, min = 10, message = "O email deve possuir no mínimo 10 caracteres.")
    String email,

    TelefoneDTO telefone,

    List<Long> idEnderecos
){
    
}