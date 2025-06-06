package main.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import main.model.cliente.Cliente;

@ApplicationScoped
public class ClienteRepository implements PanacheRepository<Cliente>{
    
    public Cliente findByCPF(String CPF){
        return find("cpf = ?1", CPF).firstResult();
    };
}
