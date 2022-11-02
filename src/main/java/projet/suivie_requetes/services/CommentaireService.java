package projet.suivie_requetes.services;

import projet.suivie_requetes.dtos.CommentaireDTO;
import projet.suivie_requetes.dtos.ModifStatusComDTO;
import projet.suivie_requetes.exceptions.CommentaireNotFoundException;
import projet.suivie_requetes.exceptions.TacheNotFoundException;

import java.util.List;

public interface CommentaireService {
    CommentaireDTO creerCommentaire(CommentaireDTO commentaireDTO) throws TacheNotFoundException;

    void deleteCommentaire(Long id) throws CommentaireNotFoundException;

    List<CommentaireDTO> listerCommentaire() throws CommentaireNotFoundException;


    void modfierStatusCommentaire(ModifStatusComDTO modifStatusComDTO) throws CommentaireNotFoundException;

    CommentaireDTO getOneCommentaire(Long id) throws CommentaireNotFoundException;

    List<CommentaireDTO> getListCommentaireParTaches(Long id) throws TacheNotFoundException;
}
