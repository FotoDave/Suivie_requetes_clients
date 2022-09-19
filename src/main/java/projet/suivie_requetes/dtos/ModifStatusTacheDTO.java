package projet.suivie_requetes.dtos;

import lombok.Data;
import projet.suivie_requetes.ennums.StatusTache;

@Data
public class ModifStatusTacheDTO {
    private Long id;
    private StatusTache statusTache;
}
