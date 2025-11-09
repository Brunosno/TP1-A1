package main.model.lote;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import main.model.DefaultEntity;
import main.model.controle.Controle;

@Entity
@Table(name = "lote")
public class Lote extends DefaultEntity {

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false)
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "controle", nullable = true)
    @JsonBackReference
    private Controle controle;


    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Controle getControle() {
        return controle;
    }

    public void setControle(Controle controle) {
        this.controle = controle;
    }
}
