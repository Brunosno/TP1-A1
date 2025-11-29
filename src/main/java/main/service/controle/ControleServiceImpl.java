package main.service.controle;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import org.jboss.resteasy.reactive.multipart.FileUpload;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import main.model.controle.Cor;
import main.model.fabricante.Fabricante;
import main.model.imagemControle.ImagemControle;
import main.model.controle.Plataforma;
import main.model.lote.Lote;
import main.dto.controleDTO.ControleDTO;
import main.dto.controleDTO.ControleResponseDTO;
import main.model.controle.Controle;
import main.repository.ControleRepository;
import main.repository.FabricanteRepository;
import main.repository.ImagemControleRepository;
import main.repository.LoteRepository;
import main.repository.PlataformaRepository;
import main.service.minio.MinioService;

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

    @Inject
    MinioService minioService;

    @Inject
    ImagemControleRepository imagemControleRepository;

    @Override
    @Transactional
    public ControleResponseDTO create(ControleDTO dto) {
        Controle novoControle = new Controle();
        novoControle.setNome(dto.nome());
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

        if (dto.idFabricante() != null) {
            Fabricante fabricante = fabricanteRepository.findById(dto.idFabricante());
            novoControle.setFabricante(fabricante);
        }

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

        if (dto.imagens() != null) {
            novoControle.setImagens(
                dto.imagens().stream().map(imgDTO -> {
                    ImagemControle img = new ImagemControle();
                    img.setUrl(imgDTO.url());
                    img.setDescricao(imgDTO.descricao());
                    img.setControle(novoControle);
                    return img;
                }).collect(Collectors.toList())
            );
        }

        ControleRepository.persist(novoControle);
        ControleRepository.flush();
        return ControleResponseDTO.valueOf(novoControle);
    }

    @Override
    @Transactional
    public ControleResponseDTO createImage(ControleDTO dto, List<FileUpload> files) {
        ControleResponseDTO novo = create(dto);

        Controle controle = ControleRepository.findById(novo.id());
        if (controle == null)
            throw new NotFoundException("Controle não encontrada com ID: " + novo.id());

        if (files == null || files.isEmpty())
            throw new IllegalArgumentException("Nenhum arquivo enviado.");

        for (FileUpload file : files) {
            try {

                var path = file.uploadedFile();

                try (InputStream is = java.nio.file.Files.newInputStream(path)) {

                    String fileName = java.util.UUID.randomUUID() + "-" + file.fileName();

                    minioService.upload(
                            fileName,
                            is,
                            file.contentType(),
                            java.nio.file.Files.size(path)
                    );

                    String url = minioService.generatePresignedUrl(fileName).toString();

                    ImagemControle imagem = new ImagemControle();
                    imagem.setUrl(url);
                    imagem.setDescricao(file.fileName());
                    imagem.setControle(controle);

                    imagemControleRepository.persist(imagem);

                    controle.getImagens().add(imagem);
                }

            } catch (Exception e) {
                throw new RuntimeException("Erro ao processar upload da imagem: " + file.fileName(), e);
            }
        }

        ControleRepository.persist(controle);

        return ControleResponseDTO.valueOf(controle);
    }


    @Override
    @Transactional
    public ControleResponseDTO update(long id, ControleDTO dto) {
        Controle edicaoControle = ControleRepository.findById(id);
        if (edicaoControle == null) {
            throw new IllegalArgumentException("Controle com ID " + id + " não encontrado.");
        }

        Fabricante fabricante = fabricanteRepository.findById(dto.idFabricante());
        if (fabricante == null) {
            throw new IllegalArgumentException("Fabricante com ID " + dto.idFabricante() + " não encontrado.");
        }

        edicaoControle.setNome(dto.nome());
        edicaoControle.setFabricante(fabricante);
        edicaoControle.setAlimentacao(dto.alimentacao());
        edicaoControle.setConexao(dto.conexao());
        edicaoControle.setTouchpad(dto.touchpad());
        edicaoControle.setGatilhosAdaptaveis(dto.gatilhosAdaptaveis());
        edicaoControle.setCor(Cor.valueOf(dto.idCor()));
        edicaoControle.setPreco(dto.preco());

        List<Plataforma> plataformas = dto.idsPlataformas()
            .stream()
            .map(idPlataforma -> plataformaRepository.findById(idPlataforma))
            .toList();
        edicaoControle.setPlataformas(plataformas);

        if (edicaoControle.getLotes() != null) {
            for (Lote antigo : edicaoControle.getLotes()) {
                antigo.setControle(null);
            }
        }

        if (dto.loteIds() == null || dto.loteIds().isEmpty()) {
            edicaoControle.setLotes(null);
            edicaoControle.setEstoque(0);
        } else {
            List<Lote> lotes = dto.loteIds().stream()
                .map(idLote -> loteRepository.findById(idLote))
                .toList();

            for (Lote lote : lotes) {
                lote.setControle(edicaoControle);
                loteRepository.persist(lote);
            }

            edicaoControle.setLotes(lotes);
            int totalEstoque = lotes.stream().mapToInt(Lote::getQuantidade).sum();
            edicaoControle.setEstoque(totalEstoque);
        }

        ControleRepository.flush();

        return ControleResponseDTO.valueOf(edicaoControle);
    }

    @Override
    @Transactional
    public ControleResponseDTO updateImage(long id, ControleDTO dto, List<FileUpload> files) {

        ControleResponseDTO atualizado = update(id, dto);

        Controle controle = ControleRepository.findById(atualizado.id());
        if (controle == null)
            throw new NotFoundException("Raquete não encontrada com ID: " + id);

        if (files == null || files.isEmpty())
            throw new IllegalArgumentException("Nenhum arquivo enviado.");

        // -------------------------------------------------------------------
        // 1. REMOVE IMAGENS ANTIGAS (DB + OPCIONAL MINIO)
        // -------------------------------------------------------------------
        if (controle.getImagens() != null && !controle.getImagens().isEmpty()) {

            for (ImagemControle imgOld : controle.getImagens()) {

                String oldFileName = imgOld.getUrl().substring(imgOld.getUrl().lastIndexOf("/") + 1);
                minioService.delete(oldFileName);

                imagemControleRepository.delete(imgOld);
            }

            controle.getImagens().clear();
        }

        // -------------------------------------------------------------------
        // 2. PROCESSA NOVAS IMAGENS — MESMA LÓGICA DO createImagens
        // -------------------------------------------------------------------
        for (FileUpload file : files) {
            try {
                var path = file.uploadedFile();

                try (InputStream is = java.nio.file.Files.newInputStream(path)) {

                    String fileName = java.util.UUID.randomUUID() + "-" + file.fileName();

                    minioService.upload(
                            fileName,
                            is,
                            file.contentType(),
                            java.nio.file.Files.size(path)
                    );

                    // URL gerada
                    String url = minioService.generatePresignedUrl(fileName).toString();

                    // Cria nova entidade de imagem
                    ImagemControle imagem = new ImagemControle();
                    imagem.setUrl(url);
                    imagem.setDescricao(file.fileName());
                    imagem.setControle(controle);

                    imagemControleRepository.persist(imagem);

                    controle.getImagens().add(imagem);
                }

            } catch (Exception e) {
                throw new RuntimeException("Erro ao atualizar imagem: " + file.fileName(), e);
            }
        }

        ControleRepository.persist(controle);

        return ControleResponseDTO.valueOf(controle);
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
