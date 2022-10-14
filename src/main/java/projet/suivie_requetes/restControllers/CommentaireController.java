package projet.suivie_requetes.restControllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import projet.suivie_requetes.dtos.ClientDTO;
import projet.suivie_requetes.dtos.CollaborateurDTO;
import projet.suivie_requetes.dtos.CommentaireDTO;
import projet.suivie_requetes.dtos.ModifStatusComDTO;
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
    public CommentaireDTO creerCommentaire(@RequestBody CommentaireDTO commentaireDTO) throws TacheNotFoundException {
        return commentaireService.creerCommentaire(commentaireDTO);
    }

    @DeleteMapping("/commentaires/{id}")
    public void deleteCommentaire(@PathVariable Long id) throws CommentaireNotFoundException {
        commentaireService.deleteCommentaire(id);
    }

    @GetMapping("/commentaires")
    public List<CommentaireDTO> listerCommentaire() throws CommentaireNotFoundException {
        return commentaireService.listerCommentaire();
    }


    @PutMapping("/commentaires/{id}")
    public void modfierStatusCommentaire(@PathVariable Long id,
            @RequestBody ModifStatusComDTO modifStatusComDTO) throws CommentaireNotFoundException {
        modifStatusComDTO.setId(id);
        commentaireService.modfierStatusCommentaire(modifStatusComDTO);
    }
}
