package controles.repository;

import controles.model.Controle;
import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ControleRepository implements PanacheRepository<Controle> {

    public List<Controle> findByMarca(String marca) {
        return find("SELECT c FROM Controle c WHERE c.marca = ?1", marca).list();
    }

    public List<Controle> findByCor(String cor) {
        return find("SELECT c FROM Controle c WHERE c.cor = ?1", cor).list();
    }

}