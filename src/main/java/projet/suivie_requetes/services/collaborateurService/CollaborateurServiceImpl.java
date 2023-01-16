package projet.suivie_requetes.services.collaborateurService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet.suivie_requetes.dtos.CollaborateurDTO;
import projet.suivie_requetes.entities.Collaborateur;
import projet.suivie_requetes.exceptions.CollaborateurNotFoundException;
import projet.suivie_requetes.mappers.DtoMapper;
import projet.suivie_requetes.repositories.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
@RequiredArgsConstructor
public class CollaborateurServiceImpl implements CollaborateurService {
    private final ClientRepository clientRepository;
    private final CollaborateurRepository collaborateurRepository;
    private final CommentaireRepository commentaireRepository;
    private final RequetteRepository requetteRepository;
    private final TacheRepository tacheRepository;
    private final DtoMapper dtoMapper;


    @Override
    public CollaborateurDTO creerCollaborateur(CollaborateurDTO collaborateurDTO) {
        log.info("Creation du collaborateur");
        Collaborateur collaborateur = dtoMapper.fromCollaborateurDTOtoCollaborateur(collaborateurDTO);
        collaborateurRepository.save(collaborateur);
        return dtoMapper.fromCollaborateurtoCollaborateurDTO(collaborateur);
    }
    @Override
    public List<CollaborateurDTO> listCollaborateurs(){
        log.info("Lister les collaborateurs");
        List<Collaborateur> collaborateurs = collaborateurRepository.findAll();
        List<CollaborateurDTO> collaborateurDTOS = collaborateurs.stream().
                map(collaborateur -> dtoMapper.fromCollaborateurtoCollaborateurDTO(collaborateur)).
                collect(Collectors.toList());
        return collaborateurDTOS;
    }

    @Override
    public List<CollaborateurDTO> searchCollaborateur(String nom) {
        List<Collaborateur> collaborateur = collaborateurRepository.searchCollaborateur(nom);
        List<CollaborateurDTO> collaborateurDTOS = collaborateur.stream().map(
                collaborateur1 -> dtoMapper.fromCollaborateurtoCollaborateurDTO(collaborateur1)
        ).collect(Collectors.toList());
        return collaborateurDTOS;
    }

    @Override
    public CollaborateurDTO getOneCollaborateur(Long id) {
        log.info("Récupération d'un collaborateur");
        Collaborateur collaborateur = collaborateurRepository.findById(id).get();
        return dtoMapper.fromCollaborateurtoCollaborateurDTO(collaborateur);
    }

    @Override
    public void deleteCollaborateur(Long id) throws CollaborateurNotFoundException {
        log.info("Suppression du collaborateur");
        if (collaborateurRepository.findById(id).isEmpty()){
            throw new CollaborateurNotFoundException();
        }
        collaborateurRepository.deleteById(id);
    }
}
