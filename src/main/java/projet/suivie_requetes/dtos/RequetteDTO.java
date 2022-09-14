package projet.suivie_requetes.dtos;

import lombok.Data;
import projet.suivie_requetes.ennums.TypeRequette;

import java.util.Date;


@Data
public class RequetteDTO {
    private Long id;
    private Date date_creation;
    private String intitule;
    private String module;
    private String fonctionnalite;
    private String urgence;
    private String observation;
    private TypeRequette typeRequette;

    private ClientDTO client;
}
