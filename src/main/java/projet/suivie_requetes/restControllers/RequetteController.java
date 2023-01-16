package projet.suivie_requetes.restControllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import projet.suivie_requetes.dtos.RequetteDTO;
import projet.suivie_requetes.exceptions.ClientNotFoundException;
import projet.suivie_requetes.exceptions.RequetteNotFoundException;
import projet.suivie_requetes.services.clientService.ClientService;
import projet.suivie_requetes.services.collaborateurService.CollaborateurService;
import projet.suivie_requetes.services.commentaireService.CommentaireService;
import projet.suivie_requetes.services.requetteService.RequetteService;
import projet.suivie_requetes.services.tacheService.TacheService;

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

    private Long cId = Long.valueOf(1);

    @PostMapping("/requettes")
    @PreAuthorize("hasAnyAuthority('Client','Admin')")
    public RequetteDTO creerRequette(@RequestBody RequetteDTO requetteDTO) throws ClientNotFoundException {
        requetteDTO.setClientId(cId);
        return requetteService.creerRequette(requetteDTO);
    }

    @GetMapping("/requettes")
    @PreAuthorize("hasAnyAuthority('Client','Admin','Collaborateur')")
    public List<RequetteDTO> listeRequettes(){
        return requetteService.listerRequette();
    }

    @GetMapping("/requettes/{id}")
    @PreAuthorize("hasAnyAuthority('Client','Admin','Collaborateur')")
    public RequetteDTO getOneRequette(@PathVariable Long id){
        return requetteService.getOneRequette(id);
    }

    @GetMapping("/requettes/search")
    @PreAuthorize("hasAnyAuthority('Client','Admin','Collaborateur')")
    public List<RequetteDTO> searchRequette(@RequestParam String nom){
        return requetteService.searchRequette("%"+nom+"%");
    }

    @PutMapping("/requettes/{id}")
    @PreAuthorize("hasAnyAuthority('Client','Admin')")
    public RequetteDTO updateRequette(@PathVariable Long id,
                                      @RequestBody RequetteDTO requetteDTO) throws ClientNotFoundException {
        requetteDTO.setId(id);
        return requetteService.creerRequette(requetteDTO);
    }

    @DeleteMapping("/requettes/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public void deleteRequette(@PathVariable Long id) throws RequetteNotFoundException {
        requetteService.deleteRequette(id);
    }
}