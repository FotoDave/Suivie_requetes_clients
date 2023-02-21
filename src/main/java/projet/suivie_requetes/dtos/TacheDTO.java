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

    /*public TacheDTO(
            Long id,
        String intitule,
        Date dateDebut,
        Date dateFin,
        String observation,
        Date debutPrevisionel,
        Date finPrevisionel,
        StatusTache statusTache,
        Date dateCreation,
        Long requetteId,
        Long collaborateurId,
        String nomCollaborateur
    ){
        this.id = id;
        this.intitule = intitule;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.observation = observation;
        this.debutPrevisionel = debutPrevisionel;
        this.finPrevisionel = finPrevisionel;
        this.statusTache = statusTache;
        this.dateCreation = dateCreation;
        this.requetteId = requetteId;
        this.collaborateurId = collaborateurId;
        this.nomCollaborateur = nomCollaborateur;
    }*/
}
