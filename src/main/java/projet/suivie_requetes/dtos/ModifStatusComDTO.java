package projet.suivie_requetes.dtos;

import com.sun.istack.NotNull;
import lombok.Data;
import projet.suivie_requetes.ennums.StatusCommenttaire;

@Data
public class ModifStatusComDTO {
    @NotNull
    private Long id;
    private StatusCommenttaire statusCommenttaire;
}
