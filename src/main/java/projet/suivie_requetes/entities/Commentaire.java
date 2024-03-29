package projet.suivie_requetes.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import projet.suivie_requetes.ennums.StatusCommenttaire;

import javax.persistence.*;
import java.util.List;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Commentaire {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String libelle;
    @Enumerated(EnumType.STRING)
    private StatusCommenttaire statusCommenttaire;

    @ManyToOne
    @JoinColumn(name = "tache_id")
    private Tache tache;
    @OneToMany(mappedBy = "requette")
    private List<FileUpload> fileUploadList;
}
