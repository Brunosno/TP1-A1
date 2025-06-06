package main.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import main.model.cliente.Telefone;

@ApplicationScoped
public class TelefoneRepository implements PanacheRepository<Telefone>{
    
    public Telefone findByNumber(String numero){
        return find( "numero = ?1", numero).firstResult();
    }
}
