package main.model.controle;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import main.model.DefaultEntity;
import main.model.pedido.Pedido;

@Entity
public class Controle extends DefaultEntity {

    @Column(length = 60, nullable = false)
    private String nome;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_fabricante", nullable = false)
    private Fabricante fabricante;

    private Cor cor;

    private Double preco;

    private String conexao;

    private String alimentacao;

    private boolean touchpad;

    private boolean gatilhosAdaptaveis;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @Column(columnDefinition = "INT CHECK (estoque >= 0)")
    private Integer estoque;

    @ManyToMany
    @JoinTable(
        name = "controle_plataforma",
        joinColumns = @JoinColumn(name = "controle_id"),
        inverseJoinColumns = @JoinColumn(name = "plataforma_id")
    )
    private List<Plataforma> plataformas;

    public List<Plataforma> getPlataformas() {
        return plataformas;
    }

    public void setPlataformas(List<Plataforma> plataformas) {
        this.plataformas = plataformas;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Fabricante getFabricante() {
        return fabricante;
    }

    public void setFabricante(Fabricante fabricante) {
        this.fabricante = fabricante;
    }

    public Cor getCor() {
        return cor;
    }

    public void setCor(Cor cor) {
        this.cor = cor;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getConexao() {
        return conexao;
    }

    public void setConexao(String conexao) {
        this.conexao = conexao;
    }

    public String getAlimentacao() {
        return alimentacao;
    }

    public void setAlimentacao(String alimentacao) {
        this.alimentacao = alimentacao;
    }

    public boolean isTouchpad() {
        return touchpad;
    }

    public void setTouchpad(boolean touchpad) {
        this.touchpad = touchpad;
    }

    public boolean isGatilhosAdaptaveis() {
        return gatilhosAdaptaveis;
    }

    public void setGatilhosAdaptaveis(boolean gatilhosAdaptaveis) {
        this.gatilhosAdaptaveis = gatilhosAdaptaveis;
    }

    public Pedido getPedido() {
        return pedido;
    }
    
    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Integer getEstoque() {
        return estoque;
    }
    
    public void setEstoque(Integer estoque) {
        this.estoque = estoque;
    }
}
