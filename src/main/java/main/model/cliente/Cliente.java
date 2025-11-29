package main.model.cliente;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Pattern;
import main.model.pessoa.Pessoa;

@Entity
public class Cliente extends Pessoa{
    
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}", message = "Formato de CPF inválido. Use XXX.XXX.XXX-XX")
    @Column(unique = true, length = 14)
    private String cpf;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
