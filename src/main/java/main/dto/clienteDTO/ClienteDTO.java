package main.dto.clienteDTO;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClienteDTO(
    @NotBlank(message = "O nome deve ser informado.")
    @Size(max = 60, message = "O nome deve possuir no máximo 60 caracteres.")
    String nome,

    @NotBlank(message = "O CPF deve ser informado.")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}", message = "Formato de CPF inválido. Use XXX.XXX.XXX-XX")
    String cpf,

    @NotBlank(message = "O e-mail deve ser informado.")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "O e-mail deve possuir no mínimo 10 caracteres.")
    @Size(max = 30, min = 10)
    String email,

    Long idTelefone,

    List<Long> idEnderecos
) {}
