package projet.suivie_requetes.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet.suivie_requetes.dtos.CommentaireDTO;
import projet.suivie_requetes.dtos.ModifStatusComDTO;
import projet.suivie_requetes.ennums.StatusCommenttaire;
import projet.suivie_requetes.entities.Commentaire;
import projet.suivie_requetes.entities.FileUpload;
import projet.suivie_requetes.entities.Tache;
import projet.suivie_requetes.exceptions.CommentaireNotFoundException;
import projet.suivie_requetes.exceptions.TacheNotFoundException;
import projet.suivie_requetes.mappers.DtoMapper;
import projet.suivie_requetes.repositories.*;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
@RequiredArgsConstructor
public class CommentaireServiceImpl implements CommentaireService {
    private final ClientRepository clientRepository;
    private final CollaborateurRepository collaborateurRepository;
    private final CommentaireRepository commentaireRepository;
    private final RequetteRepository requetteRepository;
    private final TacheRepository tacheRepository;
    private final FileUploadRepository fileUploadRepository;
    private final DtoMapper dtoMapper;


    @Override
    public CommentaireDTO creerCommentaire(CommentaireDTO commentaireDTO) throws TacheNotFoundException {
        log.info("Creation du commentaire");
        if (tacheRepository.findById(commentaireDTO.getTacheId()).isEmpty()){
            throw new TacheNotFoundException();
        }
        commentaireDTO.setStatusCommenttaire(StatusCommenttaire.NON_TRAITE);
        Tache tache = tacheRepository.findById(commentaireDTO.getTacheId()).get();
        Commentaire commentaire = dtoMapper.fromCommentaireDTOtoCommentaire(commentaireDTO);
        commentaire.setTache(tache);
        commentaireRepository.save(commentaire);
        return dtoMapper.fromCommentairetoCommentaireDTO(commentaire);
    }

    @Override
    public CommentaireDTO changeStatusCommentaire(Long id) throws CommentaireNotFoundException {
        log.info("Changement du statut du commentaire");
        Optional<Commentaire> optionalCommentaire = commentaireRepository.findById(id);
        if (optionalCommentaire.isPresent()){
            Commentaire commentaire = optionalCommentaire.get();
            commentaire.setStatusCommenttaire(StatusCommenttaire.TRAITE);
            commentaireRepository.save(commentaire);
            return dtoMapper.fromCommentairetoCommentaireDTO(commentaire);
        }else {
            throw new CommentaireNotFoundException("Commentaire not found...");
        }
    }

    @Override
    public void deleteCommentaire(Long id) throws CommentaireNotFoundException {
        log.info("Supprimer commentaire");
        if (commentaireRepository.findById(id).isEmpty()){
            throw new CommentaireNotFoundException();
        }
        commentaireRepository.deleteById(id);
    }

    @Override
    public List<CommentaireDTO> listerCommentaire() throws CommentaireNotFoundException {
        log.info("Lister Commentaires");
        List<Commentaire> commentaires = commentaireRepository.findAll();
        if (commentaires.isEmpty()){
            throw new CommentaireNotFoundException();
        }
        for (Commentaire commentaire : commentaires){
            if (fileUploadRepository.findFileUploadByCommentaireId(commentaire.getId()).isEmpty()){
                log.info("La liste des fichiers pour ce commentaire est vide....");
            }else {
                List<FileUpload> fileUploadByCommentaireId = fileUploadRepository.findFileUploadByCommentaireId(commentaire.getId());
                commentaire.setFileUploadList(fileUploadByCommentaireId);
            }
        }
        List<CommentaireDTO> commentaireDTOS = commentaires.stream().map(commentaire -> dtoMapper
                        .fromCommentairetoCommentaireDTO(commentaire))
                .collect(Collectors.toList());
        return commentaireDTOS;
    }

    @Override
    public void modfierStatusCommentaire(ModifStatusComDTO modifStatusComDTO) throws CommentaireNotFoundException {
        log.info("Modifier commentaire");
        if (commentaireRepository.findById(modifStatusComDTO.getId()).isEmpty()){
            throw new CommentaireNotFoundException();
        }
        Commentaire commentaire = commentaireRepository.findById(modifStatusComDTO.getId()).get();
        commentaire.setStatusCommenttaire(modifStatusComDTO.getStatusCommenttaire());
        commentaireRepository.save(commentaire);
    }

    @Override
    public CommentaireDTO getOneCommentaire(Long id) throws CommentaireNotFoundException {
        if(commentaireRepository.findById(id).isEmpty()){
            throw new CommentaireNotFoundException();
        }
        Commentaire commentaire = commentaireRepository.findById(id).get();
        return dtoMapper.fromCommentairetoCommentaireDTO(commentaire);
    }

    @Override
    public List<CommentaireDTO> getListCommentaireParTaches(Long id) throws TacheNotFoundException {
        if (tacheRepository.findById(id).isEmpty()){
            throw new TacheNotFoundException();
        }
        List<Commentaire> commentaires = commentaireRepository.findCommentairesByTache(id);
        for (Commentaire commentaire : commentaires){
            if (fileUploadRepository.findFileUploadByCommentaireId(commentaire.getId()).isEmpty()){
                log.info("La liste des fichiers pour ce commentaire est vide....");
            }else {
                List<FileUpload> fileUploadByCommentaireId = fileUploadRepository.findFileUploadByCommentaireId(commentaire.getId());
                commentaire.setFileUploadList(fileUploadByCommentaireId);
            }
        }
        List<CommentaireDTO> commentaireDTOS = commentaires.stream().map(commentaire ->
                                                    dtoMapper.fromCommentairetoCommentaireDTO(commentaire))
                                                    .collect(Collectors.toList());
        return commentaireDTOS;
    }
}
