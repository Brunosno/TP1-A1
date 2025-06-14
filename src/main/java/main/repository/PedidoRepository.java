package main.repository;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import main.model.pedido.Pedido;

@ApplicationScoped
public class PedidoRepository implements PanacheRepository<Pedido> {

    public List<Pedido> findByUsuario(Long usuario_id) {
        return find("usuario.id", usuario_id).list();
    }
    
}
