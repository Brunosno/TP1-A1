package main.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import main.model.imagemControle.ImagemControle;

@ApplicationScoped
public class ImagemControleRepository implements PanacheRepository<ImagemControle>{
    public ImagemControle findByName(String nome){
        return find("nome = ?1", nome).firstResult();
    }
}
