package projet.suivie_requetes.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet.suivie_requetes.dtos.CommentaireDTO;
import projet.suivie_requetes.mappers.DtoMapper;
import projet.suivie_requetes.repositories.*;

@Service
@Transactional
@Slf4j
public class CommentaireServiceImpl implements CommentaireService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CollaborateurRepository collaborateurRepository;
    @Autowired
    private CommentaireRepository commentaireRepository;
    @Autowired
    private RequetteRepository requetteRepository;
    @Autowired
    private TacheRepository tacheRepository;
    @Autowired
    private DtoMapper dtoMapper;


    @Override
    public CommentaireDTO creerCommentaire() {
        return null;
    }
}
