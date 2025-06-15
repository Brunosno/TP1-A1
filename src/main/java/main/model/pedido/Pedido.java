package main.model.pedido;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import main.model.DefaultEntity;
import main.model.cliente.Endereco;
import main.model.pagamento.TipoPagamento;
import main.model.usuario.Usuario;

@Entity
public class Pedido extends DefaultEntity{

    private LocalDate dataPedido;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pedido")
    private List<ItemPedido> itens;

    private Double total;

    private TipoPagamento tipo_pagamento;

    @ManyToOne
    @JoinColumn(name = "endereco_id", nullable = false)
    private Endereco endereco;

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public TipoPagamento getTipo_pagamento() {
        return tipo_pagamento;
    }
    

    public void setTipo_pagamento(TipoPagamento tipo_pagamento) {
        this.tipo_pagamento = tipo_pagamento;
    }

    public LocalDate getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDate dataPedido) {
            this.dataPedido = dataPedido;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

    public Double getTotal(){
        return total;
    }

    public void setTotal(Double total){
        this.total = total;
    }
}

