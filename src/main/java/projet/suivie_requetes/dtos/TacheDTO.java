package projet.suivie_requetes.dtos;

import lombok.Data;
import projet.suivie_requetes.ennums.StatusTache;

import java.util.Date;


@Data
public class TacheDTO {
    private Long id;
    private String intitule;
    private Date dateDebut;
    private Date dateFin;
    private String observation;
    private Date debutPrevisionel;
    private Date finPrevisionel;
    private StatusTache statusTache;
    private Date dateCreation;

    private Long requetteId;
    private Long collaborateurId;
    private String nomCollaborateur;
}
