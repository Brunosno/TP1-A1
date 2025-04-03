package controles.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

@Entity
public class Marca extends DefaultEntity{
    
    @OneToMany(mappedBy = "controle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Long id;

    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
