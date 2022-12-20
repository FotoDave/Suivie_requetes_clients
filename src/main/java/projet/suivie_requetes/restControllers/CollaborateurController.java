package projet.suivie_requetes.restControllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import projet.suivie_requetes.dtos.CollaborateurDTO;
import projet.suivie_requetes.exceptions.CollaborateurNotFoundException;
import projet.suivie_requetes.services.*;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@Slf4j
public class CollaborateurController {
    private final ClientService clientService;
    private final CollaborateurService collaborateurService;
    private final CommentaireService commentaireService;
    private final RequetteService requetteService;
    private final TacheService tacheService;

    @PostMapping("/collaborateurs")
    @PreAuthorize("hasAuthority('Admin')")
    public CollaborateurDTO creerCollaborateur(@RequestBody CollaborateurDTO collaborateurDTO){
        return collaborateurService.creerCollaborateur(collaborateurDTO);
    }
    @GetMapping("/collaborateurs")
    @PreAuthorize("hasAuthority('Admin')")
    public List<CollaborateurDTO> listCollaborateurs(){
        return collaborateurService.listCollaborateurs();
    }

    @GetMapping("/collaborateurs/{id}")
    @PreAuthorize("hasAnyAuthority('Admin','Collaborateur')")
    public CollaborateurDTO getOneCollaborateur(@PathVariable Long id){
        return collaborateurService.getOneCollaborateur(id);
    }

    @GetMapping("/collaborateurs/search")
    public List<CollaborateurDTO> searchCollaborateur(@RequestParam(name="keyword", defaultValue = "")
                                                          String nom){
        return collaborateurService.searchCollaborateur("%"+nom+"%");
    }

    @PutMapping("/collaborateurs/{id}")
    public CollaborateurDTO updateCollaborateur(@PathVariable Long id,
                                                @RequestBody CollaborateurDTO collaborateurDTO){
        collaborateurDTO.setId(id);
        return collaborateurService.creerCollaborateur(collaborateurDTO);
    }

    @DeleteMapping("/collaborateurs/{id}")
    public void deleteCollaborateur(@PathVariable Long id) throws CollaborateurNotFoundException {
        collaborateurService.deleteCollaborateur(id);
    }
}
