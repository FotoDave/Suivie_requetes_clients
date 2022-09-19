package projet.suivie_requetes.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class PlanifierTacheDTO {
    private Long tacheId;
    private Long collaborateurId;
    private Date dateDebutPrev;
    private Date dateFinPrev;
}
