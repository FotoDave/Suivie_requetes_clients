package projet.suivie_requetes.services;

import projet.suivie_requetes.dtos.ModifStatusTacheDTO;
import projet.suivie_requetes.dtos.PlanifierTacheDTO;
import projet.suivie_requetes.dtos.TacheDTO;
import projet.suivie_requetes.entities.Tache;
import projet.suivie_requetes.exceptions.CollaborateurNotFoundException;
import projet.suivie_requetes.exceptions.RequetteNotFoundException;
import projet.suivie_requetes.exceptions.TacheAlreadyExistException;
import projet.suivie_requetes.exceptions.TacheNotFoundException;

import java.util.List;

public interface TacheService {
    TacheDTO creerTache(TacheDTO tacheDTO) throws CollaborateurNotFoundException, RequetteNotFoundException, TacheAlreadyExistException;

    void deleteTache(Long id) throws TacheNotFoundException;

    List<TacheDTO> listerTache();
    TacheDTO getOneTache(Long id);
    List<TacheDTO> searchTache(String nom);

    TacheDTO planifierTache(PlanifierTacheDTO planifierTacheDTO) throws TacheNotFoundException;

    void modifierStatusTache(ModifStatusTacheDTO modifStatusTacheDTO) throws TacheNotFoundException;
}
