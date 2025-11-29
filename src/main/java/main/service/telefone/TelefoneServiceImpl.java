package main.service.telefone;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import main.dto.telefoneDTO.TelefoneDTO;
import main.dto.telefoneDTO.TelefoneResponseDTO;
import main.model.telefone.Telefone;
import main.repository.TelefoneRepository;

@ApplicationScoped
public class TelefoneServiceImpl implements TelefoneService{
    
    @Inject
    TelefoneRepository telefoneRepository;

    @Override
    @Transactional
    public TelefoneResponseDTO create(TelefoneDTO telefone){

        Telefone newTelefone = new Telefone();

        newTelefone.setNumero(telefone.numero());

        telefoneRepository.persist(newTelefone);

        return TelefoneResponseDTO.valueOf(newTelefone);
    }

    @Override
    @Transactional
    public void update(Long id, TelefoneDTO telefone){
        Telefone edicaoTelefone = telefoneRepository.findById(id);

        edicaoTelefone.setNumero(telefone.numero());
        telefoneRepository.persist(edicaoTelefone);
    }

    @Override
    @Transactional
    public void delete(Long id){
        Telefone telefone = telefoneRepository.findById(id);

        telefoneRepository.delete(telefone);
    }

    @Override
    public TelefoneResponseDTO findById(long id){
        return TelefoneResponseDTO.valueOf(telefoneRepository.findById(id));
    }

    @Override
    public TelefoneResponseDTO findByNumber(String numero){
        return TelefoneResponseDTO.valueOf(telefoneRepository.findByNumber(numero));
    }

    @Override
    public List<TelefoneResponseDTO> findAll(){
        return telefoneRepository.listAll().stream().map(t -> TelefoneResponseDTO.valueOf(t)).toList();
    }
}

