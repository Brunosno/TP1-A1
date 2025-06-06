package main.repository;

import jakarta.enterprise.context.ApplicationScoped;
import main.model.cliente.Endereco;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class EnderecoRepository implements PanacheRepository<Endereco> {

}