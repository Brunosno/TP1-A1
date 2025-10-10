package main.repository;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import main.model.controle.Controle;
import main.model.controle.Cor;

@ApplicationScoped
public class ControleRepository implements PanacheRepository<Controle> {

    public PanacheQuery<Controle> findAllPaginatiom(){
        return find("SELECT c FROM Controle c ORDER BY c.nome");
    }

    public PanacheQuery<Controle> findByFabricante(String fabricante) {
        return find("SELECT c FROM Controle c WHERE UPPER(c.fabricante.nome) = ?1", fabricante.toUpperCase());
    }

    public List<Controle> findByCor(String cor) {
        Cor corEnum = Cor.valueOf(cor.toUpperCase());
  
        return find("SELECT c FROM Controle c WHERE c.cor = ?1", corEnum).list();
     }

}