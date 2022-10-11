package projet.suivie_requetes.dtos;

import com.sun.istack.NotNull;
import lombok.Data;
import projet.suivie_requetes.ennums.TypeRequette;

import java.util.Date;


@Data
public class RequetteDTO {
    private Long id;
    @NotNull
    private Date date_creation;
    @NotNull
    private String intitule;
    @NotNull
    private String module;
    @NotNull
    private String fonctionnalite;
    @NotNull
    private String urgence;
    private String observation;
    private TypeRequette typeRequette;

    private Long clientId;
}
