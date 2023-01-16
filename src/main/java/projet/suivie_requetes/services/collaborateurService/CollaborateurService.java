package projet.suivie_requetes.services.collaborateurService;

import projet.suivie_requetes.dtos.CollaborateurDTO;
import projet.suivie_requetes.entities.Collaborateur;
import projet.suivie_requetes.exceptions.CollaborateurNotFoundException;

import java.util.List;

public interface CollaborateurService {
    CollaborateurDTO creerCollaborateur(CollaborateurDTO collaborateurDTO);
    List<CollaborateurDTO> listCollaborateurs();
    List<CollaborateurDTO> searchCollaborateur(String name);
    CollaborateurDTO getOneCollaborateur(Long id);
    void deleteCollaborateur(Long id) throws CollaborateurNotFoundException;
}
