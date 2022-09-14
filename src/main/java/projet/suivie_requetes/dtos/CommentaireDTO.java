package projet.suivie_requetes.dtos;

import lombok.Data;
import projet.suivie_requetes.ennums.StatusCommenttaire;


@Data
public class CommentaireDTO {
    private Long id;
    private String libelle;
    private StatusCommenttaire statusCommenttaire;

    private TacheDTO tache;
}