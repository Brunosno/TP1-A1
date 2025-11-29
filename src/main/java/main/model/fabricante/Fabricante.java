package main.model.fabricante;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Pattern;
import main.model.pessoa.Pessoa;

@Entity
public class Fabricante extends Pessoa{

    @Pattern(regexp = "\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}\\-\\d{2}", message = "Formato de CNPJ inválido. Use XX.XXX.XXX/XXXX-XX")
    @Column(unique = true)
    private String cnpj;

    public String getCNPJ(){
        return cnpj;
    }

    public void setCNPJ(String cnpj){
        if (cnpj.matches("\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}\\-\\d{2}")) {
            this.cnpj = cnpj;
        }
    }
}
