package main.service.cliente;

import java.util.List;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import main.dto.clienteDTO.ClienteDTO;
import main.dto.clienteDTO.ClienteResponseDTO;
import main.dto.telefoneDTO.TelefoneResponseDTO;
import main.model.cliente.Cliente;
import main.model.endereco.Endereco;
import main.model.telefone.Telefone;
import main.repository.ClienteRepository;
import main.repository.EnderecoRepository;
import main.repository.TelefoneRepository;
import main.service.telefone.TelefoneServiceImpl;

@ApplicationScoped
public class ClienteServiceImpl implements ClienteService {

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    TelefoneRepository telefoneRepository;

    @Inject
    TelefoneServiceImpl telefoneService;

    @Inject
    EnderecoRepository enderecoRepository;

    @Override
    @Transactional
    public ClienteResponseDTO create(ClienteDTO cliente) {
        Cliente newClient = new Cliente();

        if (cliente.telefone() == null) {
            newClient.setTelefone(null);
        } else{
            TelefoneResponseDTO telefoneNovo = telefoneService.create(cliente.telefone());

            if (telefoneNovo == null) {
                throw new IllegalArgumentException("Falha ao criar telefone para o cliente.");
            }

            Telefone telefone = telefoneRepository.findById(telefoneNovo.id());

            if (telefone == null) {
                throw new IllegalArgumentException("Telefone não encontrado com ID: " + telefoneNovo.id());
            }

            newClient.setTelefone(telefone);
        }

        if (cliente.idEnderecos() == null || cliente.idEnderecos().isEmpty()) {
            newClient.setEnderecos(null);
        } else{
            List<Endereco> enderecos = enderecoRepository.find("id in ?1", cliente.idEnderecos()).list();

            if (enderecos.size() != cliente.idEnderecos().size()) {
                throw new IllegalArgumentException("Um ou mais endereços não foram encontrados.");
            }

            newClient.setEnderecos(enderecos);
        }

        newClient.setNome(cliente.nome());
        newClient.setCpf(cliente.cpf());
        newClient.setEmail(cliente.email());

        clienteRepository.persist(newClient);

        return ClienteResponseDTO.valueOf(newClient);
    }

    @Override
    @Transactional
    public void update(Long id, ClienteDTO cliente) {
        Cliente existingClient = clienteRepository.findById(id);

        if (existingClient == null) {
            throw new IllegalArgumentException("Cliente não encontrado com ID: " + id);
        }

        if ( cliente.telefone() == null) {
        } else {

            Telefone telefone = telefoneRepository.findByNumber(cliente.telefone().numero());

            if (telefone == null) {
                TelefoneResponseDTO telefoneNovo = telefoneService.create(cliente.telefone());

                if (telefoneNovo == null) {
                    throw new IllegalArgumentException("Falha ao criar telefone para o cliente.");
                }

                telefone = telefoneRepository.findById(telefoneNovo.id());

                if (telefone == null) {
                    throw new IllegalArgumentException("Telefone não encontrado com ID: " + telefoneNovo.id());
                }

                existingClient.setTelefone(telefone);
            } else {
                existingClient.setTelefone(telefone);
            }
        }

        if ( cliente.idEnderecos() == null || cliente.idEnderecos().isEmpty()) {
            existingClient.setEnderecos(null);
        } else{
            List<Endereco> enderecos = enderecoRepository.find("id in ?1", cliente.idEnderecos()).list();

            if (enderecos.size() != cliente.idEnderecos().size()) {
                throw new IllegalArgumentException("Um ou mais endereços não foram encontrados.");
            }

            existingClient.setEnderecos(enderecos);
        }

        if (cliente.nome() != existingClient.getNome()){
            existingClient.setNome(cliente.nome());
        }

        if (cliente.cpf() != existingClient.getCpf()){
            existingClient.setCpf(cliente.cpf());
        }

        if (cliente.email() != existingClient.getEmail()){
            existingClient.setEmail(cliente.email());
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
