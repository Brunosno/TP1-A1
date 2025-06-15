package main.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import main.model.controle.Plataforma;

@ApplicationScoped
public class PlataformaRepository implements PanacheRepository<Plataforma> {
}
