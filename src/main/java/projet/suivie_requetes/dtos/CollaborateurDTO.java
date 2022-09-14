package projet.suivie_requetes.dtos;

import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Data
public class CollaborateurDTO {
    private Long id;
    private String nom;
}
