package projet.suivie_requetes.restControllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import projet.suivie_requetes.dtos.ClientDTO;
import projet.suivie_requetes.dtos.CollaborateurDTO;
import projet.suivie_requetes.services.*;

@RestController
@CrossOrigin("*")
@Slf4j
public class CommentaireController {
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
}
