package projet.suivie_requetes.restControllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;
import projet.suivie_requetes.dtos.*;
import projet.suivie_requetes.exceptions.CommentaireNotFoundException;
import projet.suivie_requetes.exceptions.TacheNotFoundException;
import projet.suivie_requetes.services.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@Slf4j
public class CommentaireController {
    private final ClientService clientService;
    private final CollaborateurService collaborateurService;
    private final CommentaireService commentaireService;
    private final RequetteService requetteService;
    private final TacheService tacheService;

    @PostMapping("/commentaires")
    @PostAuthorize("hasAuthority('Collaborateur')")
    public CommentaireDTO creerCommentaire(@RequestBody CommentaireDTO commentaireDTO) throws TacheNotFoundException {
        return commentaireService.creerCommentaire(commentaireDTO);
    }

    /*@DeleteMapping("/commentaires/{id}")
    public void deleteCommentaire(@PathVariable Long id) throws CommentaireNotFoundException {
        commentaireService.deleteCommentaire(id);
    }*/

    @GetMapping("/commentaires")
    @PostAuthorize("hasAuthority('Collaborateur')")
    public List<CommentaireDTO> listerCommentaire() throws CommentaireNotFoundException {
        return commentaireService.listerCommentaire();
    }

    @GetMapping("/commentaires/{id}")
    @PostAuthorize("hasAuthority('Collaborateur')")
    public CommentaireDTO getOneCommentaire(@PathVariable Long id) throws CommentaireNotFoundException {
        return commentaireService.getOneCommentaire(id);
    }

    @GetMapping("/commentaires/taches/{id}")
    @PostAuthorize("hasAuthority('Collaborateur')")
    public List<CommentaireDTO> getListCommentaireParTaches(@PathVariable Long id) throws TacheNotFoundException {
        return commentaireService.getListCommentaireParTaches(id);
    }

    @PutMapping("/commentaires/{id}")
    public void modfierStatusCommentaire(@PathVariable Long id,
            @RequestBody ModifStatusComDTO modifStatusComDTO) throws CommentaireNotFoundException {
        modifStatusComDTO.setId(id);
        commentaireService.modfierStatusCommentaire(modifStatusComDTO);
    }
}
