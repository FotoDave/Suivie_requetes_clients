package projet.suivie_requetes.restControllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import projet.suivie_requetes.utils.FunctionUtils;
import projet.suivie_requetes.dtos.RequetteDTO;
import projet.suivie_requetes.exceptions.ClientNotFoundException;
import projet.suivie_requetes.exceptions.RequetteNotFoundException;
import projet.suivie_requetes.exceptions.UserNotFoundException;
import projet.suivie_requetes.services.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@Slf4j
public class RequetteController {
    private final ClientService clientService;
    private final CollaborateurService collaborateurService;
    private final CommentaireService commentaireService;
    private final RequetteService requetteService;
    private final TacheService tacheService;


    @PostMapping("/requettes")
    @PreAuthorize("hasAnyAuthority('Client','Admin')")
    public RequetteDTO creerRequette(@RequestBody RequetteDTO requetteDTO) throws ClientNotFoundException, UserNotFoundException {
        return requetteService.creerRequette(requetteDTO);
    }

    @GetMapping("/requettes")
    @PreAuthorize("hasAnyAuthority('Client','Admin','Collaborateur')")
    public List<RequetteDTO> listeRequettes() throws ClientNotFoundException, UserNotFoundException {
        return requetteService.listerRequette();
    }

    @GetMapping("/requette/filter")
    @PreAuthorize("hasAnyAuthority('Client','Admin','Collaborateur')")
    public List<RequetteDTO> filtrerRequette(
            @RequestParam(name = "typeRequette", required = false, defaultValue = "") String typeRequette,
            @RequestParam(name = "statusRequette", required = false, defaultValue = "") String statusRequette,
            @RequestParam(name = "intitule", required = false, defaultValue = "") String intitule,
            @RequestParam(name = "idClient", required = false, defaultValue = "") String idClient,
            @RequestParam(name = "id", required = false, defaultValue = "") String id
    ) {
        Long idValue = FunctionUtils.convertStringToLong(id);
        Long idClientValue = FunctionUtils.convertStringToLong(idClient);
        return requetteService.filterRequette(typeRequette, statusRequette, intitule, idClientValue, idValue);
    }

    @GetMapping("/requettes/{id}")
    @PreAuthorize("hasAnyAuthority('Client','Admin','Collaborateur')")
    public RequetteDTO getOneRequette(@PathVariable Long id){
        return requetteService.getOneRequette(id);
    }

    @PutMapping("/requettes/estimatedStartDate")
    @PreAuthorize("hasAnyAuthority('Admin','Collaborateur')")
    public RequetteDTO estimatedStartDate(@RequestBody RequetteDTO requetteDTO) throws RequetteNotFoundException {
        return requetteService.estimatedStartDate(requetteDTO);
    }

    @GetMapping("/requettes/search")
    @PreAuthorize("hasAnyAuthority('Client','Admin','Collaborateur')")
    public List<RequetteDTO> searchRequette(@RequestParam String nom){
        return requetteService.searchRequette("%"+nom+"%");
    }

    @PutMapping("/requettes/{id}")
    @PreAuthorize("hasAnyAuthority('Client','Admin')")
    public RequetteDTO updateRequette(@PathVariable Long id,
                                      @RequestBody RequetteDTO requetteDTO) throws ClientNotFoundException, UserNotFoundException {
        requetteDTO.setId(id);
        return requetteService.creerRequette(requetteDTO);
    }

    @DeleteMapping("/requettes/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public void deleteRequette(@PathVariable Long id) throws RequetteNotFoundException {
        requetteService.deleteRequette(id);
    }
}