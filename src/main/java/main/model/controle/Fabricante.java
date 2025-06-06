package main.model.controle;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import main.model.pessoa.PessoaJuridica;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Fabricante extends PessoaJuridica{
    
    @OneToMany(mappedBy = "fabricante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Controle> controles;

    public List<Controle> getControles() {
        return controles;
    }

    public void setControles(List<Controle> controles) {
        this.controles = controles;
    }
}
