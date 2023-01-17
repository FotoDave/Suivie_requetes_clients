package projet.suivie_requetes.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet.suivie_requetes.dtos.ClientDTO;
import projet.suivie_requetes.entities.Client;
import projet.suivie_requetes.exceptions.ClientNotFoundException;
import projet.suivie_requetes.mappers.DtoMapper;
import projet.suivie_requetes.repositories.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final CollaborateurRepository collaborateurRepository;
    private final CommentaireRepository commentaireRepository;
    private final RequetteRepository requetteRepository;
    private final TacheRepository tacheRepository;
    private final DtoMapper dtoMapper;

    @Override
    public ClientDTO creerClient(ClientDTO clientDTO) {
        log.info("Creation du client");
        Client client = dtoMapper.fromClientDTOtoClient(clientDTO);
        clientRepository.save(client);
        return dtoMapper.fromClienttoClientDTO(client);
    }

    @Override
    public ClientDTO oneClient(Long id) {
        log.info("Récupération d'un client");
        ClientDTO clientDTO = dtoMapper.fromClienttoClientDTO(clientRepository.findById(id).get());
        return clientDTO;
    }

    @Override
    public List<ClientDTO> listClients(){
        log.info("Lister clients");
        List<Client> clients = clientRepository.findAll();
        List<ClientDTO> clientDTOS = clients.stream()
                .map(client -> dtoMapper.fromClienttoClientDTO(client))
                .collect(Collectors.toList());
        return clientDTOS;
    }

    @Override
    public List<ClientDTO> searchClients(String keyword) {
        log.info("Recherche des clients");
        List<Client> clients = clientRepository.searchClient(keyword);
        List<ClientDTO> search = clients.stream().map(
                client -> dtoMapper.fromClienttoClientDTO(client)).
                collect(Collectors.toList());
        return search;
    }

    @Override
    public void deleteClient(Long id) throws ClientNotFoundException {
        log.info("Suppression du client");
        if (clientRepository.findById(id).isEmpty()) {
            throw new ClientNotFoundException();
        }
        clientRepository.deleteById(id);
    }
}
