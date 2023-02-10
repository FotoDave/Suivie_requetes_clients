package projet.suivie_requetes.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import projet.suivie_requetes.ennums.StatusTache;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Tache {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String intitule;
    private Date dateDebut;
    private Date dateFin;
    private String observation;
    private Date debutPrevisionel;
    private Date finPrevisionel;
    private Date dateCreation;
    @Enumerated(EnumType.STRING)
    private StatusTache statusTache;

    @OneToMany(mappedBy = "tache")
    private List<Commentaire> commentaireList;
    @OneToMany(mappedBy = "requette")
    private List<FileUpload> fileUploadList;
    @ManyToOne
    private Requette requette;
    @ManyToOne
    private Collaborateur collaborateur;
}
