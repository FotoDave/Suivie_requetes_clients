package projet.suivie_requetes.dtos;

import com.sun.istack.NotNull;
import lombok.Data;
import projet.suivie_requetes.ennums.StatusCommenttaire;
import projet.suivie_requetes.entities.FileUpload;

import java.util.List;


@Data
public class CommentaireDTO {
    private Long id;
    private String libelle;
    private StatusCommenttaire statusCommenttaire;
    private Long tacheId;
    private String fileName;
    private String fileCode;
    //private List<FileUploadDTO> fileUploadList;
}
