package projet.suivie_requetes.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
public class FileUpload {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String downloadUri;

    @Column(unique = true)
    private String fileCode;
    private Long size;
    @ManyToOne
    @JoinColumn(name = "file.requette_id")
    private Requette requette;
    @ManyToOne
    @JoinColumn(name = "file.commentaire_id")
    private Commentaire commentaire;
    @ManyToOne
    @JoinColumn(name = "file.tache_id")
    private Tache tache;
}
