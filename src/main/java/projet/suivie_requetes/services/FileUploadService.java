package projet.suivie_requetes.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import projet.suivie_requetes.dtos.FileUploadDTO;
import projet.suivie_requetes.dtos.ModifStatusTacheDTO;
import projet.suivie_requetes.dtos.TacheDTO;
import projet.suivie_requetes.exceptions.*;

import java.io.IOException;
import java.util.List;

public interface FileUploadService {
    FileUploadDTO uploadFile(MultipartFile multipartFile)
            throws IOException, TacheNotFoundException, RequetteNotFoundException, CommentaireNotFoundException;

    Resource getFileAsResource(String fileCode) throws IOException;
}
