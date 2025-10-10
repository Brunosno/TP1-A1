package main.service.controle;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;
import main.model.controle.Cor;
import main.model.controle.Fabricante;
import main.model.controle.Plataforma;
import main.dto.controleDTO.ControleDTO;
import main.dto.controleDTO.ControleResponseDTO;
import main.model.controle.Controle;
import main.repository.ControleRepository;
import main.repository.FabricanteRepository;
import main.repository.PlataformaRepository;

@ApplicationScoped
public class ControleServiceImpl implements ControleService {

    @Inject
    ControleRepository ControleRepository;

    @Inject
    FabricanteRepository fabricanteRepository;

    @Inject
    PlataformaRepository plataformaRepository;

    @Override
    @Transactional
    public ControleResponseDTO create(ControleDTO Controle) {
        Controle novoControle = new Controle();

        Fabricante fabricante = fabricanteRepository.findById(Controle.idFabricante());

        novoControle.setNome(Controle.nome());
        novoControle.setFabricante(fabricante);
        novoControle.setAlimentacao(Controle.alimentacao());
        novoControle.setConexao(Controle.conexao());
        novoControle.setTouchpad(Controle.touchpad());
        novoControle.setGatilhosAdaptaveis(Controle.gatilhosAdaptaveis());
        novoControle.setCor(Cor.valueOf(Controle.idCor()));
        novoControle.setPreco(Controle.preco());
        novoControle.setEstoque(Controle.quantidade());

        List<Plataforma> plataformas = Controle.idsPlataformas()
            .stream()
            .map(id -> plataformaRepository.findById(id))
            .toList();

        novoControle.setPlataformas(plataformas);

        ControleRepository.persist(novoControle);

        return ControleResponseDTO.valueOf(novoControle);
    }

    @Override
    @Transactional
    public void update(long id, ControleDTO Controle) {
        Controle edicaoControle = ControleRepository.findById(id);
        Fabricante fabricante = fabricanteRepository.findById(Controle.idFabricante());

        edicaoControle.setNome(Controle.nome());
        edicaoControle.setFabricante(fabricante);
        edicaoControle.setAlimentacao(Controle.alimentacao());
        edicaoControle.setConexao(Controle.conexao());
        edicaoControle.setTouchpad(Controle.touchpad());
        edicaoControle.setGatilhosAdaptaveis(Controle.gatilhosAdaptaveis());
        edicaoControle.setCor(Cor.valueOf(Controle.idCor()));
        edicaoControle.setPreco(Controle.preco());
        edicaoControle.setEstoque(Controle.quantidade());

        List<Plataforma> plataformas = Controle.idsPlataformas()
            .stream()
            .map(idPlataforma -> plataformaRepository.findById(idPlataforma))
            .toList();
            
        edicaoControle.setPlataformas(plataformas);
    }

    @Override
    @Transactional
    public void delete(long id) {
        try {
            ControleRepository.deleteById(id);
        } catch (Exception e) {
            throw new Error("Erro ao apagar controle " + id, e);
        }
    }

    @Override
    public ControleResponseDTO findById(long id) {
        return ControleResponseDTO.valueOf(ControleRepository.findById(id));
    }

    @Override
    public List<ControleResponseDTO> findByFabricante(String fabricante, int page, int pageSize) {
        return ControleResponseDTO.listOf(ControleRepository.findByFabricante(fabricante).page(page, pageSize).list());
    }

    @Override
    public List<ControleResponseDTO> findByCor(String cor) {
        return ControleResponseDTO.listOf(ControleRepository.findByCor(cor));
    }

    @Override
    public List<ControleResponseDTO> findAll(int page, int pageSize) {
        return ControleRepository.findAllPaginatiom().page(page, pageSize).list().stream().map(c -> ControleResponseDTO.valueOf(c)).toList();
    }
    
}
