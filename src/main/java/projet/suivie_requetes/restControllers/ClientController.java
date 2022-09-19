package projet.suivie_requetes.restControllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import projet.suivie_requetes.dtos.ClientDTO;
import projet.suivie_requetes.exceptions.ClientNotFoundException;
import projet.suivie_requetes.services.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@Slf4j
public class ClientController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private CollaborateurService collaborateurService;
    @Autowired
    private CommentaireService commentaireService;
    @Autowired
    private RequetteService requetteService;
    @Autowired
    private TacheService tacheService;

    @PostMapping("/clients")
    public ClientDTO creerClient(@RequestBody ClientDTO clientDTO){

        return clientService.creerClient(clientDTO);
    }

    @GetMapping("/clients")
    public List<ClientDTO> listClients(){

        return clientService.listClients();
    }

    @PutMapping("/clients/{id}")
    public ClientDTO updateClient(@PathVariable Long id,
                                  @RequestBody ClientDTO clientDTO){
        clientDTO.setId(id);
        return clientService.creerClient(clientDTO);
    }
    @DeleteMapping("/clients/{id}")
    public void deleteClient(@PathVariable Long id) throws ClientNotFoundException {
        clientService.deleteClient(id);
    }
}
