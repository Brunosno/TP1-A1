package main.service.fabricante;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import main.dto.fabricanteDTO.FabricanteDTO;
import main.dto.fabricanteDTO.FabricanteResponseDTO;
import main.model.cliente.Endereco;
import main.model.cliente.Telefone;
import main.model.controle.Fabricante;
import main.repository.EnderecoRepository;
import main.repository.FabricanteRepository;
import main.repository.TelefoneRepository;

@ApplicationScoped
public class FabricanteServiceImpl implements FabricanteService {

    @Inject
    FabricanteRepository fabricanteRepository;

    @Inject
    TelefoneRepository telefoneRepository;

    @Inject
    EnderecoRepository enderecoRepository;

    @Override
    @Transactional
    public FabricanteResponseDTO create(FabricanteDTO fabricanteDTO) {
        Fabricante novoFabricante = new Fabricante();

        Telefone telefone = telefoneRepository.findById(fabricanteDTO.idtelefone());

        List<Endereco> enderecos = enderecoRepository.find("id in ?1", fabricanteDTO.idEnderecos()).list();
        if (enderecos.size() != fabricanteDTO.idEnderecos().size()) {
            throw new IllegalArgumentException("Um ou mais endereços não foram encontrados.");
        }

        novoFabricante.setNome(fabricanteDTO.nome());
        novoFabricante.setCNPJ(fabricanteDTO.cnpj());
        novoFabricante.setEmail(fabricanteDTO.email());
        novoFabricante.setTelefone(telefone);
        novoFabricante.setEnderecos(enderecos);

        fabricanteRepository.persist(novoFabricante);

        return FabricanteResponseDTO.valueOf(novoFabricante);
    }

    @Override
    @Transactional
    public void update(Long id, FabricanteDTO fabricanteDTO) {
        Fabricante fabricanteExistente = fabricanteRepository.findById(id);

        Telefone telefone = telefoneRepository.findById(fabricanteDTO.idtelefone());

        List<Endereco> enderecos = enderecoRepository.find("id in ?1", fabricanteDTO.idEnderecos()).list();
        if (enderecos.size() != fabricanteDTO.idEnderecos().size()) {
            throw new IllegalArgumentException("Um ou mais endereços não foram encontrados.");
        }

        if (fabricanteExistente != null) {
            fabricanteExistente.setNome(fabricanteDTO.nome());
            fabricanteExistente.setCNPJ(fabricanteDTO.cnpj());
            fabricanteExistente.setEmail(fabricanteDTO.email());
            fabricanteExistente.setTelefone(telefone);
            fabricanteExistente.setEnderecos(enderecos);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Fabricante fabricante = fabricanteRepository.findById(id);
        if (fabricante != null) {
            fabricanteRepository.delete(fabricante);
        }
    }

    @Override
    public List<FabricanteResponseDTO> findAll() {
        return fabricanteRepository.listAll().stream()
            .map(FabricanteResponseDTO::valueOf)
            .toList();
    }

    @Override
    public FabricanteResponseDTO findById(Long id) {
        return FabricanteResponseDTO.valueOf(fabricanteRepository.findById(id));
    }

    @Override
    public FabricanteResponseDTO findByCNPJ(String cnpj) {
        return FabricanteResponseDTO.valueOf(fabricanteRepository.findByCNPJ(cnpj));
    }
}
