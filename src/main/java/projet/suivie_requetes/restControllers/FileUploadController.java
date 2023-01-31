package projet.suivie_requetes.restControllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import projet.suivie_requetes.dtos.FileUploadDTO;
import projet.suivie_requetes.exceptions.CommentaireNotFoundException;
import projet.suivie_requetes.exceptions.RequetteNotFoundException;
import projet.suivie_requetes.exceptions.TacheNotFoundException;
import projet.suivie_requetes.services.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@Slf4j
public class FileUploadController {
    private final ClientService clientService;
    private final CollaborateurService collaborateurService;
    private final CommentaireService commentaireService;
    private final RequetteService requetteService;
    private final TacheService tacheService;
    private final FileUploadService fileUploadService;
    private List<FileItem> fileItems;

    @PostMapping("/file")
    @PreAuthorize("hasAnyAuthority('Admin', 'Collaborateur', 'Client')")
    public ResponseEntity<FileUploadDTO> upload(@RequestParam("file") MultipartFile file)
            throws IOException, CommentaireNotFoundException, TacheNotFoundException, RequetteNotFoundException {
        return new ResponseEntity<>(fileUploadService.uploadFile(file), HttpStatus.OK);
    }

    @GetMapping("/files/{fileCode}")
    @PreAuthorize("hasAnyAuthority('Admin', 'Collaborateur', 'Client')")
    public ResponseEntity<?> downloadFile(@PathVariable("fileCode") String fileCode) throws IOException {
        Resource resource = null;
        try {
            resource = fileUploadService.getFileAsResource(fileCode);
        }catch (IOException e){
            return ResponseEntity.internalServerError().build();
        }

        if (resource == null){
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        }
        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\""+resource.getFilename()+"\"";
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }
}
