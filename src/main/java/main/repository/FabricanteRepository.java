package main.repository;


import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import main.model.controle.Fabricante;

@ApplicationScoped
public class FabricanteRepository implements PanacheRepository<Fabricante>{
    
    public Fabricante findByCNPJ(String cnpj){
        return find("cnpj = ?1", cnpj).firstResult();
    };
    
}
