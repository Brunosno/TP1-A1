package service;

import java.util.List;

import dto.ControleDTO;
import dto.ControleResponseDTO;
import model.Controle;
import model.Cor;
import repository.ControleRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ControleServiceImpl implements ControleService {

    @Inject
    ControleRepository ControleRepository;

    @Override
    @Transactional
    public ControleResponseDTO create(ControleDTO Controle) {
        Controle novoControle = new Controle();
        novoControle.setNome(Controle.nome());
        novoControle.setMarca(Controle.marca());
       
        novoControle.setCor(Cor.valueOf(Controle.idCor()));

        // realizando inclusao
        ControleRepository.persist(novoControle);

        return ControleResponseDTO.valueOf(novoControle);
    }

    @Override
    @Transactional
    public void update(long id, ControleDTO Controle) {
        Controle edicaoControle = ControleRepository.findById(id);

        edicaoControle.setNome(Controle.nome());
        edicaoControle.setMarca(Controle.marca());
        edicaoControle.setCor(Cor.valueOf(Controle.idCor()));
    }

    @Override
    @Transactional
    public void delete(long id) {
        ControleRepository.deleteById(id);
    }

    @Override
    public ControleResponseDTO findById(long id) {
        return ControleResponseDTO.valueOf(ControleRepository.findById(id));
    }

    @Override
    public ControleResponseDTO findByMarca(String marca) {
        return ControleResponseDTO.valueOf(ControleRepository.findBySigla(marca));
    }

    @Override
    public List<ControleResponseDTO> findAll() {
        return ControleRepository.findAll().stream().map(c -> ControleResponseDTO.valueOf(c)).toList();
    }
    
}
