package projet.suivie_requetes.restControllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;
import projet.suivie_requetes.dtos.ClientDTO;
import projet.suivie_requetes.exceptions.ClientNotFoundException;
import projet.suivie_requetes.services.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@Slf4j
public class ClientController {
    private final ClientService clientService;
    private final CollaborateurService collaborateurService;
    private final CommentaireService commentaireService;
    private final RequetteService requetteService;
    private final TacheService tacheService;

    @PostMapping("/clients")
    @PostAuthorize("hasAuthority('Admin')")
    public ClientDTO creerClient(@RequestBody ClientDTO clientDTO){
        return clientService.creerClient(clientDTO);
    }

    @GetMapping("/clients")
    @PostAuthorize("hasAuthority('Admin')")
    public List<ClientDTO> listClients(){
        return clientService.listClients();
    }

    @GetMapping("/clients/{id}")
    @PostAuthorize("hasAuthority('Admin')")
    public ClientDTO listClients(@PathVariable Long id){
        return clientService.oneClient(id);
    }

    @GetMapping("/clients/search")
    @PostAuthorize("hasAuthority('Admin')")
    public List<ClientDTO> searchClients(@RequestParam(name = "keyword", defaultValue ="")
                                             String keyword){
        return clientService.searchClients("%"+keyword+"%");
    }

    @PutMapping("/clients/{id}")
    @PostAuthorize("hasAuthority('Admin')")
    public ClientDTO updateClient(@PathVariable Long id,
                                  @RequestBody ClientDTO clientDTO){
        clientDTO.setId(id);
        return clientService.creerClient(clientDTO);
    }
    @DeleteMapping("/clients/{id}")
    @PostAuthorize("hasAuthority('Admin')")
    public void deleteClient(@PathVariable Long id) throws ClientNotFoundException {
        clientService.deleteClient(id);
    }
}
