package main.service.cliente;

import java.util.List;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import main.dto.clienteDTO.ClienteDTO;
import main.dto.clienteDTO.ClienteResponseDTO;
import main.model.cliente.Cliente;
import main.model.cliente.Endereco;
import main.model.cliente.Telefone;
import main.repository.ClienteRepository;
import main.repository.EnderecoRepository;
import main.repository.TelefoneRepository;

@ApplicationScoped
public class ClienteServiceImpl implements ClienteService {

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    TelefoneRepository telefoneRepository;

    @Inject
    EnderecoRepository enderecoRepository;

    @Override
    @Transactional
    public ClienteResponseDTO create(ClienteDTO clienteDTO) {
        Cliente newClient = new Cliente();

        Telefone telefone = telefoneRepository.findById(clienteDTO.idTelefone());
        
        List<Endereco> enderecos = enderecoRepository.find("id in ?1", clienteDTO.idEnderecos()).list();

        if (enderecos.size() != clienteDTO.idEnderecos().size()) {
            throw new IllegalArgumentException("Um ou mais endereços não foram encontrados.");
        }

        newClient.setNome(clienteDTO.nome());
        newClient.setCpf(clienteDTO.cpf());
        newClient.setEmail(clienteDTO.email());
        newClient.setTelefone(telefone);
        newClient.setEnderecos(enderecos);

        clienteRepository.persist(newClient);

        return ClienteResponseDTO.valueOf(newClient);
    }

    @Override
    @Transactional
    public void update(Long id, ClienteDTO clienteDTO) {
        Cliente existingClient = clienteRepository.findById(id);
        Telefone telefone = telefoneRepository.findById(clienteDTO.idTelefone());

        List<Endereco> enderecos = enderecoRepository.find("id in ?1", clienteDTO.idEnderecos()).list();

        if (enderecos.size() != clienteDTO.idEnderecos().size()) {
            throw new IllegalArgumentException("Um ou mais endereços não foram encontrados.");
        }

        if (existingClient != null) {
            existingClient.setNome(clienteDTO.nome());
            existingClient.setCpf(clienteDTO.cpf());
            existingClient.setEmail(clienteDTO.email());
            existingClient.setTelefone(telefone);
            existingClient.setEnderecos(enderecos);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Cliente cliente = clienteRepository.findById(id);
        if (cliente != null) {
            clienteRepository.delete(cliente);
        }
    }

    @Override
    public List<ClienteResponseDTO> findAll() {
        return clienteRepository.listAll().stream().map(ClienteResponseDTO::valueOf).toList();
    }

    @Override
    public ClienteResponseDTO findById(Long id) {
        return ClienteResponseDTO.valueOf(clienteRepository.findById(id));
    }

    @Override
    public ClienteResponseDTO findByCPF(String cpf) {
        return ClienteResponseDTO.valueOf(clienteRepository.findByCPF(cpf));
    }
}
