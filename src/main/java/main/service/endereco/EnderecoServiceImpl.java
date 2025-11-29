package main.service.endereco;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import main.dto.enderecoDTO.EnderecoDTO;
import main.dto.enderecoDTO.EnderecoResponseDTO;
import main.model.endereco.Endereco;
import main.repository.EnderecoRepository;

import java.util.List;

@ApplicationScoped
public class EnderecoServiceImpl implements EnderecoService {

    @Inject
    EnderecoRepository enderecoRepository;

    @Override
    @Transactional
    public EnderecoResponseDTO create(EnderecoDTO dto) {

        Endereco endereco = new Endereco();
        endereco.setRua(dto.rua());
        endereco.setNumero(dto.numero());
        endereco.setBairro(dto.bairro());
        endereco.setCidade(dto.cidade());
        endereco.setEstado(dto.estado());
        endereco.setCep(dto.cep());

        enderecoRepository.persist(endereco);

        return EnderecoResponseDTO.valueOf(endereco);
    }

    @Override
    @Transactional
    public void update(Long id, EnderecoDTO dto) {
        Endereco endereco = enderecoRepository.findById(id);
        if (endereco == null) {
            throw new RuntimeException("Endereço não encontrado com ID: " + id);
        }

        endereco.setRua(dto.rua());
        endereco.setNumero(dto.numero());
        endereco.setBairro(dto.bairro());
        endereco.setCidade(dto.cidade());
        endereco.setEstado(dto.estado());
        endereco.setCep(dto.cep());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Endereco endereco = enderecoRepository.findById(id);
        if (endereco == null) {
            throw new RuntimeException("Endereço não encontrado com ID: " + id);
        }
        enderecoRepository.delete(endereco);
    }

    @Override
    public EnderecoResponseDTO findById(Long id) {
        Endereco endereco = enderecoRepository.findById(id);
        if (endereco == null) {
            throw new RuntimeException("Endereço não encontrado com ID: " + id);
        }
        return EnderecoResponseDTO.valueOf(endereco);
    }

    @Override
    public EnderecoResponseDTO findByCEP(String cep){
        Endereco endereco = enderecoRepository.findByCEP(cep);
        if (endereco == null) {
            throw new RuntimeException("Endereço não encontrado com cep: " + cep);
        }
        return EnderecoResponseDTO.valueOf(endereco);
    }

    @Override
    public List<EnderecoResponseDTO> findAll() {
        return enderecoRepository.listAll().stream()
            .map(EnderecoResponseDTO::valueOf)
            .toList();
    }
}