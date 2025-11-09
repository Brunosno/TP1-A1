package main.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import main.model.cliente.Endereco;

@ApplicationScoped
public class EnderecoRepository implements PanacheRepository<Endereco> {

    public Endereco findByCEP(String cep) {
        return find("cep like ?1", cep).firstResult();
    }

    public PanacheQuery<Endereco> findAllPagination() {
        return find("SELECT e FROM Endereco e ORDER BY e.id");
    }
}
