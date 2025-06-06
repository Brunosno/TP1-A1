package main.model.cliente;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Pattern;
import main.model.DefaultEntity;

@Entity
public class Telefone extends DefaultEntity {

    @Pattern(
        regexp = "\\(\\d{2}\\) \\d{4,5}\\-\\d{4}",
        message = "Número de telefone inválido. Use o formato (XX) XXXXX-XXXX ou (XX) XXXX-XXXX"
    )
    private String numero;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        if (numero.matches("\\(\\d{2}\\) \\d{4,5}-\\d{4}")) {
            this.numero = numero;
        }
    }
}
