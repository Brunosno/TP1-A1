package main.model.controle;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Column;
import main.model.DefaultEntity;
import java.util.List;

@Entity
public class Plataforma extends DefaultEntity {

    @Column(nullable = false, unique = true)
    private String nome;

    @ManyToMany(mappedBy = "plataformas")
    private List<Controle> controles;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Controle> getControles() {
        return controles;
    }

    public void setControles(List<Controle> controles) {
        this.controles = controles;
    }
}