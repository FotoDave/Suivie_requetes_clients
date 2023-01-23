package projet.suivie_requetes.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import projet.suivie_requetes.dtos.FileUploadDTO;
import projet.suivie_requetes.entities.Commentaire;
import projet.suivie_requetes.entities.FileUpload;
import projet.suivie_requetes.entities.Requette;
import projet.suivie_requetes.entities.Tache;
import projet.suivie_requetes.exceptions.CommentaireNotFoundException;
import projet.suivie_requetes.exceptions.RequetteNotFoundException;
import projet.suivie_requetes.exceptions.TacheNotFoundException;
import projet.suivie_requetes.mappers.DtoMapper;
import projet.suivie_requetes.repositories.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {
    private Path foundFile;
    private final ClientRepository clientRepository;
    private final CollaborateurRepository collaborateurRepository;
    private final CommentaireRepository commentaireRepository;
    private final RequetteRepository requetteRepository;
    private final TacheRepository tacheRepository;
    private final FileUploadRepository fileUploadRepository;
    private final DtoMapper dtoMapper;
    @Override
    public FileUploadDTO uploadFile(MultipartFile multipartFile) throws IOException, TacheNotFoundException, RequetteNotFoundException, CommentaireNotFoundException {
        log.info("Enregistrement fichiers à uploader...");
        FileUploadDTO fileUploadDTO = new FileUploadDTO();
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        fileUploadDTO.setSize(multipartFile.getSize());
        Path directory = Paths.get("/home/wallace/Documents/Prog_Spring/Suivie_requetes/Files-Uploaded");
        fileUploadDTO.setFileCode(RandomStringUtils.randomAlphanumeric(10));
        try {
            InputStream inputStream = multipartFile.getInputStream();
            Path filePath = directory.resolve(fileUploadDTO.getFileCode()+"-"+fileName);
            fileUploadDTO.setFileName(String.valueOf(filePath.getFileName()));
            log.info("Récupération du fichier");
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            inputStream.close();
        }catch (Exception exception){
            throw new IOException("Erreur d'enregistrement du fichier : "+fileUploadDTO.getFileName(), exception);
        }

        FileUpload fileUpload = dtoMapper.fromFileUploadDtoToFileUpload(fileUploadDTO);
        if(fileUploadDTO.getTacheId() != null){
            if (tacheRepository.findById(fileUploadDTO.getTacheId()).isPresent()){
                Tache tache = tacheRepository.findById(fileUploadDTO.getTacheId()).get();
                fileUpload.setTache(tache);
            }else{
                throw new TacheNotFoundException("Taches not found");
            }
        } else if (fileUploadDTO.getRequetteId() != null) {
            if (requetteRepository.findById(fileUploadDTO.getRequetteId()).isPresent()){
                Requette requette = requetteRepository.findById(fileUploadDTO.getRequetteId()).get();
                fileUpload.setRequette(requette);
            }else {
                throw new RequetteNotFoundException("Requette not found");
            }
        } else if (fileUploadDTO.getCommentaireId() != null) {
            if (commentaireRepository.findById(fileUploadDTO.getCommentaireId()).isPresent()){
                Commentaire commentaire =commentaireRepository.findById(fileUploadDTO.getCommentaireId()).get();
                fileUpload.setCommentaire(commentaire);
            }else {
                throw new CommentaireNotFoundException("Commentaire not found");
            }
        }

        fileUploadRepository.save(fileUpload);
        log.info("Fichier enregistré");

        return dtoMapper.fromFileUploadToFileUploadDto(fileUpload);
    }

    @Override
    public Resource getFileAsResource(String fileCode) throws IOException {
        log.info("Téléchargement des fichiers");
        Path directory = Paths.get("Files-Uploaded");
        Files.list(directory).forEach(files ->{
            if(files.getFileName().toString().startsWith(fileCode)){
                foundFile = files;
            }
        });
        if(foundFile != null){
            return new UrlResource(foundFile.toUri());
        }
        return null;
    }
}
