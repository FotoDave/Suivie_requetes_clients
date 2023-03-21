package projet.suivie_requetes.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import projet.suivie_requetes.ennums.StatusRequette;
import projet.suivie_requetes.ennums.TypeRequette;
import projet.suivie_requetes.security.entities.AppUser;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Requette {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date_creation;
    private String intitule;
    private String module;
    private String fonctionnalite;
    private String urgence;
    @Column(columnDefinition = "TEXT")
    private String observation;
    @Enumerated(EnumType.STRING)
    private TypeRequette typeRequette;
    @Enumerated(EnumType.STRING)
    private StatusRequette statusRequette;

    @OneToMany(mappedBy = "requette")
    private List<Tache> tacheList;
    @OneToMany(mappedBy = "requette")
    private List<FileUpload> fileUploadList;

    @ManyToOne
    @JoinColumn(name = "requette.appUser_id")
    private AppUser appUser;
}
