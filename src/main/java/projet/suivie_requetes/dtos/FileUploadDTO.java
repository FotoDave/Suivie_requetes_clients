package projet.suivie_requetes.dtos;

import lombok.Data;

@Data
public class FileUploadDTO {
    private Long id;
    private String fileName;
    private String downloadUri;
    private String fileCode;
    private Long size;
    private Long requetteId;
    private Long tacheId;
    private Long commentaireId;
    private String element;
    private Long elementId;
}
