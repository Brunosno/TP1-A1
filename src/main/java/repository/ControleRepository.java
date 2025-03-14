package repository;

import model.Controle;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ControleRepository implements PanacheRepository<Controle> {

    public Controle findBySigla(String marca) {
        return find("SELECT c FROM Controle c WHERE c.marca = ?1 ", marca).firstResult();
    }

}