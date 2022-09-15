package projet.suivie_requetes.restControllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import projet.suivie_requetes.dtos.CollaborateurDTO;
import projet.suivie_requetes.services.*;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@CrossOrigin("*")
@Slf4j
public class CollaborateurController {
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

    @PostMapping("/collaborateurs")
    public CollaborateurDTO creerCollaborateur(@RequestBody CollaborateurDTO collaborateurDTO){
        return collaborateurService.creerCollaborateur(collaborateurDTO);
    }
    @GetMapping("/collaborateurs")
    public List<CollaborateurDTO> listCollaborateurs(){
        return collaborateurService.listCollaborateurs();
    }

    @PutMapping("/collaborateurs/{id}")
    public CollaborateurDTO updateCollaborateur(@PathVariable Long id,
                                                @RequestBody CollaborateurDTO collaborateurDTO){
        collaborateurDTO.setId(id);
        return collaborateurService.creerCollaborateur(collaborateurDTO);
    }

    @DeleteMapping("/collaborateurs/{id}")
    public void deleteCollaborateur(@PathVariable Long id){
        collaborateurService.deleteCollaborateur(id);
    }
}
