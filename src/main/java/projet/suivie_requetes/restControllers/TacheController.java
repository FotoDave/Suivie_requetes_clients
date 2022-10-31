package projet.suivie_requetes.restControllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public TacheDTO creerTache(@RequestBody TacheDTO tacheDTO) throws CollaborateurNotFoundException, RequetteNotFoundException, TacheAlreadyExistException {
        return tacheService.creerTache(tacheDTO);
    }

    @GetMapping("/taches")
    public List<TacheDTO> listerTache(){
        return tacheService.listerTache();
    }

    @GetMapping("/taches/searchTache")
    public List<TacheDTO> searchTacheByRequetteIdOrStatusTache(@RequestParam String requetteId, @RequestParam String statusTache) throws RequetteNotFoundException, StatusNotFoundException {
        return tacheService.searchTacheByRequetteIdOrStatusTache(requetteId, statusTache);
    }

    @GetMapping("/taches/{id}")
    public TacheDTO getOneTache(@PathVariable Long id){
        return tacheService.getOneTache(id);
    }

    @GetMapping("/taches/search")
    public List<TacheDTO> searchTache(@RequestParam String nom){
        return tacheService.searchTache("%"+nom+"%");
    }

    @PutMapping("/taches/{id}")
    public TacheDTO updateTache(@PathVariable Long id,
                                @RequestBody TacheDTO tacheDTO) throws CollaborateurNotFoundException, RequetteNotFoundException, TacheAlreadyExistException {
        tacheDTO.setId(id);
        return tacheService.creerTache(tacheDTO);
    }

    @DeleteMapping("/taches/{id}")
    public void deleteTache(@PathVariable Long id) throws TacheNotFoundException {
        tacheService.deleteTache(id);
    }

    @PostMapping("/planifierTache")
    public TacheDTO planifierTache(@RequestBody PlanifierTacheDTO planifierTacheDTO) throws TacheNotFoundException {
        return tacheService.planifierTache(planifierTacheDTO);
    }

    @PutMapping("/modifierStatusTache/{id}")
    public void modifierStatusTache(@PathVariable Long id,
                                    @RequestBody ModifStatusTacheDTO modifStatusTacheDTO) throws TacheNotFoundException {
        tacheService.modifierStatusTache(modifStatusTacheDTO);
    }
}
