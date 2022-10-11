package projet.suivie_requetes.dtos;

import com.sun.istack.NotNull;
import lombok.Data;
import projet.suivie_requetes.ennums.StatusCommenttaire;


@Data
public class CommentaireDTO {
    private Long id;
    @NotNull
    private String libelle;
    @NotNull
    private StatusCommenttaire statusCommenttaire;
    @NotNull
    private Long tacheId;
}
