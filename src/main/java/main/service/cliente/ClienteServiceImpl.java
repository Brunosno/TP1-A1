package main.service.cliente;

import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import main.dto.clienteDTO.ClienteDTO;
import main.dto.clienteDTO.ClienteResponseDTO;
import main.dto.telefoneDTO.TelefoneResponseDTO;
import main.dto.enderecoDTO.EnderecoDTO;
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

    //=========================
    //     CREATE CLIENTE
    //=========================
    @Override
    @Transactional
    public ClienteResponseDTO create(ClienteDTO cliente) {
        Cliente newClient = new Cliente();

        // --- TELEFONE ---
        if (cliente.telefone() != null) {
            TelefoneResponseDTO telefoneNovo = telefoneService.create(cliente.telefone());
            Telefone telefone = telefoneRepository.findById(telefoneNovo.id());
            newClient.setTelefone(telefone);
        }

        if (cliente.enderecos() != null && !cliente.enderecos().isEmpty()) {
            List<Endereco> enderecos = new ArrayList<>();

            for (EnderecoDTO e : cliente.enderecos()) {
                Endereco endereco = new Endereco();
                endereco.setRua(e.rua());
                endereco.setNumero(e.numero());
                endereco.setBairro(e.bairro());
                endereco.setCidade(e.cidade());
                endereco.setEstado(e.estado());
                endereco.setCep(e.cep());

                enderecoRepository.persist(endereco);
                enderecos.add(endereco);
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

        if (cliente.telefone() != null) {
            Telefone telefone = telefoneRepository.findByNumber(cliente.telefone().numero());

            if (telefone == null) {
                TelefoneResponseDTO telNovo = telefoneService.create(cliente.telefone());
                telefone = telefoneRepository.findById(telNovo.id());
            }
            existingClient.setTelefone(telefone);
        }

        if (cliente.enderecos() != null && !cliente.enderecos().isEmpty()) {
            List<Endereco> novosEnderecos = new ArrayList<>();

            for (EnderecoDTO e : cliente.enderecos()) {
                Endereco endereco = new Endereco();
                endereco.setRua(e.rua());
                endereco.setNumero(e.numero());
                endereco.setBairro(e.bairro());
                endereco.setCidade(e.cidade());
                endereco.setEstado(e.estado());
                endereco.setCep(e.cep());

                enderecoRepository.persist(endereco);
                novosEnderecos.add(endereco);
            }

            existingClient.setEnderecos(novosEnderecos);
        }

        if (cliente.nome() != null && !cliente.nome().isBlank())
            existingClient.setNome(cliente.nome());

        if (cliente.email() != null && !cliente.email().isBlank())
            existingClient.setEmail(cliente.email());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Cliente cliente = clienteRepository.findById(id);
        if (cliente != null) clienteRepository.delete(cliente);
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
