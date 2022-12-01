package projet.suivie_requetes.restControllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;
import projet.suivie_requetes.dtos.RequetteDTO;
import projet.suivie_requetes.exceptions.ClientNotFoundException;
import projet.suivie_requetes.exceptions.RequetteNotFoundException;
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

    private Long cId = Long.valueOf(1);

    @PostMapping("/requettes")
    @PostAuthorize("hasAuthority('Client')")
    public RequetteDTO creerRequette(@RequestBody RequetteDTO requetteDTO) throws ClientNotFoundException {
        requetteDTO.setClientId(cId);
        return requetteService.creerRequette(requetteDTO);
    }

    @GetMapping("/requettes")
    @PostAuthorize("hasAuthority('Client')")
    public List<RequetteDTO> listeRequettes(){
        return requetteService.listerRequette();
    }

    @GetMapping("/requettes/{id}")
    @PostAuthorize("hasAuthority('Client')")
    public RequetteDTO getOneRequette(@PathVariable Long id){
        return requetteService.getOneRequette(id);
    }

    @GetMapping("/requettes/search")
    @PostAuthorize("hasAuthority('Client')")
    public List<RequetteDTO> searchRequette(@RequestParam String nom){
        return requetteService.searchRequette("%"+nom+"%");
    }

    @PutMapping("/requettes/{id}")
    @PostAuthorize("hasAuthority('Client')")
    public RequetteDTO updateRequette(@PathVariable Long id,
                                      @RequestBody RequetteDTO requetteDTO) throws ClientNotFoundException {
        requetteDTO.setId(id);
        return requetteService.creerRequette(requetteDTO);
    }

    @DeleteMapping("/requettes/{id}")
    @PostAuthorize("hasAuthority('Admin')")
    public void deleteRequette(@PathVariable Long id) throws RequetteNotFoundException {
        requetteService.deleteRequette(id);
    }
}