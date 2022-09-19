package projet.suivie_requetes.restControllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import projet.suivie_requetes.dtos.RequetteDTO;
import projet.suivie_requetes.exceptions.ClientNotFoundException;
import projet.suivie_requetes.exceptions.RequetteNotFoundException;
import projet.suivie_requetes.services.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@Slf4j
public class RequetteController {
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

    @PostMapping("/requettes")
    public RequetteDTO creerRequette(@RequestBody RequetteDTO requetteDTO) throws ClientNotFoundException {
        return requetteService.creerRequette(requetteDTO);
    }

    @GetMapping("/requettes")
    public List<RequetteDTO> listeRequettes(){
        return requetteService.listerRequette();
    }

    @PutMapping("/requettes/{id}")
    public RequetteDTO updateRequette(@PathVariable Long id,
                                      @RequestBody RequetteDTO requetteDTO) throws ClientNotFoundException {
        requetteDTO.setId(id);
        return requetteService.creerRequette(requetteDTO);
    }

    @DeleteMapping("/requettes/{id}")
    public void deleteRequette(@PathVariable Long id) throws RequetteNotFoundException {
        requetteService.deleteRequette(id);
    }
}
