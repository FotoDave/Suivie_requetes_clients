package projet.suivie_requetes.services;

import projet.suivie_requetes.dtos.ModifStatusTacheDTO;
import projet.suivie_requetes.dtos.PlanifierTacheDTO;
import projet.suivie_requetes.dtos.SearchDTO;
import projet.suivie_requetes.dtos.TacheDTO;
import projet.suivie_requetes.entities.Tache;
import projet.suivie_requetes.exceptions.*;

import java.util.Date;
import java.util.List;

public interface TacheService {
    TacheDTO creerTache(TacheDTO tacheDTO) throws CollaborateurNotFoundException, RequetteNotFoundException, TacheAlreadyExistException;
    TacheDTO modifierStatutTache(TacheDTO tacheDTO) throws TacheNotFoundException, RequetteNotFoundException;
    List<TacheDTO> filterTaches(Long idTache, Long idReq,
                                Long idCollab, String statut,
                                Date dateDebut, Date dateFin,
                                Date dateDebutPrev, Date dateFinPrev);
    List<TacheDTO> listerTache();
    TacheDTO getOneTache(Long id) throws TacheNotFoundException;
    TacheDTO planifierTache(TacheDTO tacheDTO) throws TacheNotFoundException;
    TacheDTO modifierTache(TacheDTO tacheDTO) throws TacheNotFoundException;
}
