package projet.suivie_requetes.services;

import projet.suivie_requetes.dtos.CollaborateurDTO;
import projet.suivie_requetes.entities.Collaborateur;

import java.util.List;

public interface CollaborateurService {
    CollaborateurDTO creerCollaborateur(CollaborateurDTO collaborateurDTO);
    List<CollaborateurDTO> listCollaborateurs();

    void deleteCollaborateur(Long id);
}
