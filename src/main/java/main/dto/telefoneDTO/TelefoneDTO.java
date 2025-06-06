package main.dto.telefoneDTO;

import jakarta.validation.constraints.Pattern;

public record TelefoneDTO(

    @Pattern(regexp = "\\(\\d{2}\\) \\d{5}-\\d{4}", message = "Formato de telefone inválido. Use (XX) XXXXX-XXXX")
    String numero

){}
