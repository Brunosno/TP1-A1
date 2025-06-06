package main.model.pedido;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import main.model.DefaultEntity;
import main.model.controle.Controle;
import main.model.pagamento.TipoPagamento;
import main.model.usuario.Usuario;

@Entity
public class Pedido extends DefaultEntity{

    private LocalDate dataPedido;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Usuario cliente;

    @ManyToMany
    @JoinTable(
        name = "pedido_controle",
        joinColumns = @JoinColumn(name = "pedido_id"),
        inverseJoinColumns = @JoinColumn(name = "controle_id")
    )
    private List<Controle> controles;

    private Integer preco;

    private TipoPagamento tipo_pagamento;

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

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    public List<Controle> getControles() {
        return controles;
    }

    public void setControles(List<Controle> controles) {
        this.controles = controles;
    }

    public Integer getPreco(){
        return preco;
    }

    public void setPreco(Integer preco){
        this.preco = preco;
    }
    
}

