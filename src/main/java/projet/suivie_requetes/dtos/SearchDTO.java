package projet.suivie_requetes.dtos;

import lombok.Data;
import projet.suivie_requetes.ennums.StatusTache;

import java.util.Date;


@Data
public class SearchDTO {
    private Long requetteId;
    private StatusTache statusTache;

}
