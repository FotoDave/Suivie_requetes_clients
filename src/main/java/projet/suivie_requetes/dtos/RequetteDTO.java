package projet.suivie_requetes.dtos;

import com.sun.istack.NotNull;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import projet.suivie_requetes.ennums.StatusRequette;
import projet.suivie_requetes.ennums.TypeRequette;
import projet.suivie_requetes.entities.Requette;

import java.util.Date;


@Data
public class RequetteDTO {
    private Long id;
    private Date date_creation;
    private Date estimatedStartDate;
    private String intitule;
    private String module;
    private String fonctionnalite;
    private String urgence;
    private String observation;
    private TypeRequette typeRequette;
    private StatusRequette statusRequette;

    private String username;
    private String nomClient;
    private Long clientId;
    /*public static RequetteDTO mapFromRequette(Requette requette){
        RequetteDTO requetteDTO = new RequetteDTO();
        BeanUtils.copyProperties(requette, requetteDTO);
        requetteDTO.setUsername(requette.getAppUser().getUsername());
        //requetteDTO.setClientId(requette.getClient().getId());
        return requetteDTO;
    }*/
}
