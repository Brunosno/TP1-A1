package main.service.controle;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import main.model.controle.Cor;
import main.model.controle.Fabricante;
import main.model.controle.Plataforma;
import main.model.lote.Lote;
import main.dto.controleDTO.ControleDTO;
import main.dto.controleDTO.ControleResponseDTO;
import main.model.controle.Controle;
import main.repository.ControleRepository;
import main.repository.FabricanteRepository;
import main.repository.LoteRepository;
import main.repository.PlataformaRepository;

@ApplicationScoped
public class ControleServiceImpl implements ControleService {

    @Inject
    ControleRepository ControleRepository;

    @Inject
    FabricanteRepository fabricanteRepository;

    @Inject
    PlataformaRepository plataformaRepository;

    @Inject
    LoteRepository loteRepository;

    @Override
    @Transactional
    public ControleResponseDTO create(ControleDTO dto) {
        Controle novoControle = new Controle();

        Fabricante fabricante = fabricanteRepository.findById(dto.idFabricante());
        novoControle.setNome(dto.nome());
        novoControle.setFabricante(fabricante);
        novoControle.setAlimentacao(dto.alimentacao());
        novoControle.setConexao(dto.conexao());
        novoControle.setTouchpad(dto.touchpad());
        novoControle.setGatilhosAdaptaveis(dto.gatilhosAdaptaveis());
        novoControle.setCor(Cor.valueOf(dto.idCor()));
        novoControle.setPreco(dto.preco());

        List<Plataforma> plataformas = dto.idsPlataformas()
            .stream()
            .map(id -> plataformaRepository.findById(id))
            .toList();
        novoControle.setPlataformas(plataformas);

        ControleRepository.persist(novoControle);
        ControleRepository.flush();

        if (dto.loteIds() != null && !dto.loteIds().isEmpty()) {
            List<Lote> lotes = dto.loteIds().stream()
                .map(id -> loteRepository.findById(id))
                .toList();

            for (Lote l : lotes) {
                l.setControle(novoControle);
            }

            Integer totalEstoque = lotes.stream().mapToInt(Lote::getQuantidade).sum();
            novoControle.setEstoque(totalEstoque);
            novoControle.setLotes(lotes);
        } else {
            novoControle.setEstoque(0);
        }

        return ControleResponseDTO.valueOf(novoControle);
    }


    @Override
    @Transactional
    public void update(long id, ControleDTO dto) {
        Controle edicaoControle = ControleRepository.findById(id);
        if (edicaoControle == null) {
            throw new IllegalArgumentException("Controle com ID " + id + " não encontrado.");
        }

        Fabricante fabricante = fabricanteRepository.findById(dto.idFabricante());
        if (fabricante == null) {
            throw new IllegalArgumentException("Fabricante com ID " + dto.idFabricante() + " não encontrado.");
        }

        // Atualiza atributos básicos
        edicaoControle.setNome(dto.nome());
        edicaoControle.setFabricante(fabricante);
        edicaoControle.setAlimentacao(dto.alimentacao());
        edicaoControle.setConexao(dto.conexao());
        edicaoControle.setTouchpad(dto.touchpad());
        edicaoControle.setGatilhosAdaptaveis(dto.gatilhosAdaptaveis());
        edicaoControle.setCor(Cor.valueOf(dto.idCor()));
        edicaoControle.setPreco(dto.preco());

        // Atualiza plataformas
        List<Plataforma> plataformas = dto.idsPlataformas()
            .stream()
            .map(idPlataforma -> plataformaRepository.findById(idPlataforma))
            .toList();
        edicaoControle.setPlataformas(plataformas);

        // Limpa relacionamentos antigos de lotes
        if (edicaoControle.getLotes() != null) {
            for (Lote antigo : edicaoControle.getLotes()) {
                antigo.setControle(null);
            }
        }

        // Atualiza novos lotes
        if (dto.loteIds() == null || dto.loteIds().isEmpty()) {
            edicaoControle.setLotes(null);
            edicaoControle.setEstoque(0);
        } else {
            List<Lote> lotes = dto.loteIds().stream()
                .map(idLote -> loteRepository.findById(idLote))
                .toList();

            for (Lote lote : lotes) {
                lote.setControle(edicaoControle);
                loteRepository.persist(lote); // força vínculo
            }

            edicaoControle.setLotes(lotes);
            int totalEstoque = lotes.stream().mapToInt(Lote::getQuantidade).sum();
            edicaoControle.setEstoque(totalEstoque);
        }

        ControleRepository.flush(); // garante persistência
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
        return ControleRepository.findAllPagination().page(page, pageSize).list().stream().map(c -> ControleResponseDTO.valueOf(c)).toList();
    }

    @Override
    public Long count(){
        return ControleRepository.count();
    }
}
