package main.model.usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import main.model.DefaultEntity;
import main.model.cliente.Cliente;

@Entity
public class Usuario extends DefaultEntity{
    
    @Column(length = 30, unique = true)
    private String username;

    @Column(length = 88)
    private String senha;

    private Perfil perfil;

    @OneToOne
    @JoinColumn(name = "id_cliente", unique = true)
    private Cliente cliente;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    
}
