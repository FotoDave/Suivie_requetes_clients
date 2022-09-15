package projet.suivie_requetes.services;

import projet.suivie_requetes.dtos.ClientDTO;

import java.util.List;

public interface ClientService {
    ClientDTO creerClient(ClientDTO clientDTO);

    List<ClientDTO> listClients();

    void deleteClient(Long id);
}
