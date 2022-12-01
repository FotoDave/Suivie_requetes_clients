package projet.suivie_requetes.restControllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;
import projet.suivie_requetes.dtos.*;
import projet.suivie_requetes.exceptions.*;
import projet.suivie_requetes.services.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@Slf4j
public class TacheController {
    private final ClientService clientService;
    private final CollaborateurService collaborateurService;
    private final CommentaireService commentaireService;
    private final RequetteService requetteService;
    private final TacheService tacheService;

    @PostMapping("/taches")
    @PostAuthorize("hasAuthority('Admin')")
    public TacheDTO creerTache(@RequestBody TacheDTO tacheDTO) throws CollaborateurNotFoundException, RequetteNotFoundException, TacheAlreadyExistException {
        return tacheService.creerTache(tacheDTO);
    }

    @GetMapping("/taches")
    @PostAuthorize("hasAuthority('Collaborateur')")
    public List<TacheDTO> listerTache(){
        return tacheService.listerTache();
    }

    @GetMapping("/taches/searchTache")
    @PostAuthorize("hasAuthority('Collaborateur')")
    public List<TacheDTO> searchTacheByRequetteIdOrStatusTache(@RequestParam String requetteId, @RequestParam String statusTache) throws RequetteNotFoundException, StatusNotFoundException {
        return tacheService.searchTacheByRequetteIdOrStatusTache(requetteId, statusTache);
    }

    @GetMapping("/taches/{id}")
    @PostAuthorize("hasAuthority('Collaborateur')")
    public TacheDTO getOneTache(@PathVariable Long id){
        return tacheService.getOneTache(id);
    }

    @PutMapping("/taches/modifier")
    @PostAuthorize("hasAuthority('Admin')")
    public TacheDTO modifierTache(@RequestBody TacheDTO tacheDTO) throws TacheNotFoundException {
        return tacheService.modifierTache(tacheDTO);
    }

    @PutMapping("/taches/planifier")
    @PostAuthorize("hasAuthority('Admin')")
    public TacheDTO planifierTache(@RequestBody TacheDTO tacheDTO) throws TacheNotFoundException {
        return tacheService.planifierTache(tacheDTO);
    }

    /*@PutMapping("/modifierStatusTache/{id}")
    public void modifierStatusTache(@PathVariable Long id,
                                    @RequestBody ModifStatusTacheDTO modifStatusTacheDTO) throws TacheNotFoundException {
        tacheService.modifierStatusTache(modifStatusTacheDTO);
    }*/

    /*@GetMapping("/taches/search")
    public List<TacheDTO> searchTache(@RequestParam String nom){
        return tacheService.searchTache("%"+nom+"%");
    }*/

    /*@DeleteMapping("/taches/{id}")
    public void deleteTache(@PathVariable Long id) throws TacheNotFoundException {
        tacheService.deleteTache(id);
    }*/
}