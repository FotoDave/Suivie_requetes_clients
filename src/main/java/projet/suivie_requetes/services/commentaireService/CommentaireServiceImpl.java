package projet.suivie_requetes.services.commentaireService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet.suivie_requetes.dtos.CommentaireDTO;
import projet.suivie_requetes.dtos.ModifStatusComDTO;
import projet.suivie_requetes.ennums.StatusCommenttaire;
import projet.suivie_requetes.entities.Commentaire;
import projet.suivie_requetes.entities.Tache;
import projet.suivie_requetes.exceptions.CommentaireNotFoundException;
import projet.suivie_requetes.exceptions.TacheNotFoundException;
import projet.suivie_requetes.mappers.DtoMapper;
import projet.suivie_requetes.repositories.*;

import java.util.List;
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
    private final DtoMapper dtoMapper;


    @Override
    public CommentaireDTO creerCommentaire(CommentaireDTO commentaireDTO) throws TacheNotFoundException {
        if (tacheRepository.findById(commentaireDTO.getTacheId()).isEmpty()){
            throw new TacheNotFoundException();
        }
        log.info("Creation du commentaire");
        commentaireDTO.setStatusCommenttaire(StatusCommenttaire.NON_TRAITE);
        Tache tache = tacheRepository.findById(commentaireDTO.getTacheId()).get();
        Commentaire commentaire = dtoMapper.fromCommentaireDTOtoCommentaire(commentaireDTO);
        commentaire.setTache(tache);
        commentaireRepository.save(commentaire);
        return dtoMapper.fromCommentairetoCommentaireDTO(commentaire);
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
        List<CommentaireDTO> commentaireDTOS = commentaires.stream().map(commentaire ->
                                                    dtoMapper.fromCommentairetoCommentaireDTO(commentaire))
                                                    .collect(Collectors.toList());
        return commentaireDTOS;
    }
}
