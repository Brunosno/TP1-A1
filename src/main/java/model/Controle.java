package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Controle extends DefaultEntity {

    @Column(length = 60, nullable = false)
    private String nome;

    @Column(length = 2, nullable = false)
    private String marca;

    private Cor cor;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Cor getCor() {
        return cor;
    }

    public void setCor(Cor cor) {
        this.cor = cor;
    }

    

}
