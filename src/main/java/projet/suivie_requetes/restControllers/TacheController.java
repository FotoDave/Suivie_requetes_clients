package projet.suivie_requetes.restControllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import projet.suivie_requetes.dtos.*;
import projet.suivie_requetes.exceptions.*;
import projet.suivie_requetes.services.*;
import projet.suivie_requetes.utils.FunctionUtils;

import java.util.Date;
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
    @PreAuthorize("hasAuthority('Admin')")
    public TacheDTO creerTache(@RequestBody TacheDTO tacheDTO) throws CollaborateurNotFoundException, RequetteNotFoundException, TacheAlreadyExistException {
        return tacheService.creerTache(tacheDTO);
    }
    @GetMapping("/tache/filter")
    @PreAuthorize("hasAnyAuthority('Admin','Collaborateur')")
    public List<TacheDTO> filtrerRequette(
            @RequestParam(name = "idTache", required = false, defaultValue = "") String idTache,
            @RequestParam(name = "idReq", required = false, defaultValue = "") String idReq,
            @RequestParam(name = "idCollab", required = false, defaultValue = "") String idCollab,
            @RequestParam(name = "statut", required = false, defaultValue = "") String statut
            /*@RequestParam(name = "dateDebut", required = false, defaultValue = "") String dateDebut,
            @RequestParam(name = "dateFin", required = false, defaultValue = "") String dateFin,
            @RequestParam(name = "dateDebutPrev", required = false, defaultValue = "") String dateDebutPrev,
            @RequestParam(name = "dateFinPrev", required = false, defaultValue = "") String dateFinPrev*/) {
        Long idTacheValue = FunctionUtils.convertStringToLong(idTache);
        Long idReqValue = FunctionUtils.convertStringToLong(idReq);
        Long idCollabValue = FunctionUtils.convertStringToLong(idCollab);
        /*Date dateDebutValue = FunctionUtils.convertStringToDate(dateDebut);
        Date dateFinValue = FunctionUtils.convertStringToDate(dateFin);
        Date dateDebutPrevValue = FunctionUtils.convertStringToDate(dateDebutPrev);
        Date dateFinPrevValue = FunctionUtils.convertStringToDate(dateFinPrev);*/
        return tacheService.filterTaches(idTacheValue, idReqValue, idCollabValue, statut/*,
                dateDebutValue, dateFinValue, dateDebutPrevValue, dateFinPrevValue*/);
    }

    @GetMapping("/taches")
    @PreAuthorize("hasAnyAuthority('Collaborateur','Admin')")
    public List<TacheDTO> listerTache(){
        return tacheService.listerTache();
    }

    @GetMapping("/taches/{id}")
    @PreAuthorize("hasAnyAuthority('Collaborateur','Admin')")
    public TacheDTO getOneTache(@PathVariable Long id) throws TacheNotFoundException {
        return tacheService.getOneTache(id);
    }

    @PutMapping("/taches/modifier")
    @PreAuthorize("hasAnyAuthority('Collaborateur','Admin')")
    public TacheDTO modifierTache(@RequestBody TacheDTO tacheDTO) throws TacheNotFoundException {
        return tacheService.modifierTache(tacheDTO);
    }

    @PutMapping("/taches/changeStatus")
    @PreAuthorize("hasAnyAuthority('Collaborateur','Admin')")
    public TacheDTO changeStatusTache(@RequestBody TacheDTO tacheDTO) throws TacheNotFoundException, RequetteNotFoundException {
        return tacheService.modifierStatutTache(tacheDTO);
    }

    @PutMapping("/taches/planifier")
    @PreAuthorize("hasAnyAuthority('Collaborateur','Admin')")
    public TacheDTO planifierTache(@RequestBody TacheDTO tacheDTO) throws TacheNotFoundException {
        return tacheService.planifierTache(tacheDTO);
    }
}