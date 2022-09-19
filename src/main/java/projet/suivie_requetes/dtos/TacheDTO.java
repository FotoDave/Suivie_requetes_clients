package projet.suivie_requetes.dtos;

import lombok.Data;
import projet.suivie_requetes.ennums.StatusTache;

import java.util.Date;


@Data
public class TacheDTO {
    private Long id;
    private String intitule;
    private Date date_debut;
    private Date date_fin;
    private String observation;
    private Date debut_previsionel;
    private Date fin_previsionel;
    private StatusTache statusTache;

    private Long requetteId;
    private Long collaborateurId;
}
