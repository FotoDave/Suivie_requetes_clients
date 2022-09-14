package projet.suivie_requetes.services;

import projet.suivie_requetes.dtos.*;

public interface SuivieService {
    RequetteDTO CreerRequette(RequetteDTO requetteDTO);
    ClientDTO CreerClient(ClientDTO clientDTO);
    CollaborateurDTO CreerCollaborrateur(CollaborateurDTO collaborateurDTO);
    CommentaireDTO CreerCommentaire(CommentaireDTO commentaireDTO);
    TacheDTO CreerTache(TacheDTO tacheDTO);
    TacheDTO TacheEffectue();
    TacheDTO AffecterTache();
    CommentaireDTO InsererCommentaire();
    TacheDTO PlanifierTache();

}
