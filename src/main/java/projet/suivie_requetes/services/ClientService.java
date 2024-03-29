package projet.suivie_requetes.services;

import projet.suivie_requetes.dtos.ClientDTO;
import projet.suivie_requetes.exceptions.ClientNotFoundException;

import java.util.List;

public interface ClientService {
    ClientDTO creerClient(ClientDTO clientDTO);

    ClientDTO oneClient(Long id);

    List<ClientDTO> listClients();

    List<ClientDTO> searchClients(String keyword);

    void deleteClient(Long id) throws ClientNotFoundException;

}
