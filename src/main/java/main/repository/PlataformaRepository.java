package main.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import main.model.controle.Plataforma;

@ApplicationScoped
public class PlataformaRepository implements PanacheRepository<Plataforma> {

    public PanacheQuery<Plataforma> findAllPagination() {
        return find("SELECT p FROM Plataforma p ORDER BY p.nome");
    }
}
