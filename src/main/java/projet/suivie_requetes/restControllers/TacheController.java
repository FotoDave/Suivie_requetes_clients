package projet.suivie_requetes.restControllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import projet.suivie_requetes.dtos.ClientDTO;
import projet.suivie_requetes.dtos.CollaborateurDTO;
import projet.suivie_requetes.dtos.PlanifierTacheDTO;
import projet.suivie_requetes.dtos.TacheDTO;
import projet.suivie_requetes.exceptions.CollaborateurNotFoundException;
import projet.suivie_requetes.exceptions.RequetteNotFoundException;
import projet.suivie_requetes.exceptions.TacheAlreadyExistException;
import projet.suivie_requetes.exceptions.TacheNotFoundException;
import projet.suivie_requetes.services.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@Slf4j
public class TacheController {
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

    @PostMapping("/taches")
    public TacheDTO creerTache(@RequestBody TacheDTO tacheDTO) throws CollaborateurNotFoundException, RequetteNotFoundException, TacheAlreadyExistException {
        return tacheService.creerTache(tacheDTO);
    }

    @GetMapping("/taches")
    public List<TacheDTO> listerTache(){
        return tacheService.listerTache();
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
}
