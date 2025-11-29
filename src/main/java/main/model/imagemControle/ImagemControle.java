package main.model.imagemControle;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import main.model.DefaultEntity;
import main.model.controle.Controle;

@Entity
public class ImagemControle extends DefaultEntity{
    
    @Column(length = 1000)
    private String url;

    private String descricao;

    @ManyToOne
    @JoinColumn(name = "id_controle")
    private Controle controle;

    public void setUrl(String url){
        this.url = url;
    }

    public String getUrl(){
        return this.url;
    }

    public void setDescricao(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao(){
        return this.descricao;
    }
    

    public Controle getControle() {
        return controle;
    }

    public void setControle(Controle controle) {
        this.controle = controle;
    }
}
