package projet.suivie_requetes.services.fileUploadService;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import projet.suivie_requetes.dtos.FileUploadDTO;
import projet.suivie_requetes.exceptions.CommentaireNotFoundException;
import projet.suivie_requetes.exceptions.RequetteNotFoundException;
import projet.suivie_requetes.exceptions.TacheNotFoundException;

import java.io.IOException;

public interface FileUploadService {
    FileUploadDTO uploadFile(MultipartFile multipartFile)
            throws IOException, TacheNotFoundException, RequetteNotFoundException, CommentaireNotFoundException;

    Resource getFileAsResource(String fileCode) throws IOException;
}
