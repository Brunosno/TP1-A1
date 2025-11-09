package main.repository;

import jakarta.enterprise.context.ApplicationScoped;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import main.model.lote.Lote;

@ApplicationScoped
public class LoteRepository implements PanacheRepository<Lote> {

}
