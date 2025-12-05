package main.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import main.model.lote.Lote;

@ApplicationScoped
public class LoteRepository implements PanacheRepository<Lote> {

    public PanacheQuery<Lote> findAllPagination() {
        return find("SELECT l FROM Lote l ORDER BY l.id DESC");
    }
}
