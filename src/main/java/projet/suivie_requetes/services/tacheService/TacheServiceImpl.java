package projet.suivie_requetes.services.tacheService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet.suivie_requetes.dtos.ModifStatusTacheDTO;
import projet.suivie_requetes.dtos.TacheDTO;
import projet.suivie_requetes.ennums.StatusTache;
import projet.suivie_requetes.entities.Collaborateur;
import projet.suivie_requetes.entities.Requette;
import projet.suivie_requetes.entities.Tache;
import projet.suivie_requetes.exceptions.RequetteNotFoundException;
import projet.suivie_requetes.exceptions.StatusNotFoundException;
import projet.suivie_requetes.exceptions.TacheAlreadyExistException;
import projet.suivie_requetes.exceptions.TacheNotFoundException;
import projet.suivie_requetes.mappers.DtoMapper;
import projet.suivie_requetes.repositories.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.core.io.NumberInput.parseLong;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
@RequiredArgsConstructor
public class TacheServiceImpl implements TacheService {
    private String badge;
    private final ClientRepository clientRepository;
    private final CollaborateurRepository collaborateurRepository;
    private final CommentaireRepository commentaireRepository;
    private final RequetteRepository requetteRepository;
    private final TacheRepository tacheRepository;
    private final DtoMapper dtoMapper;

    @Override
    public TacheDTO  creerTache(TacheDTO tacheDTO) throws RequetteNotFoundException, TacheAlreadyExistException {
        log.info("Creation d'une Tache");
        tacheDTO.setStatusTache(StatusTache.NON_PLANIFIE);
        tacheDTO.setDateCreation(new Date());
        Optional<Requette> requette = requetteRepository.findById(tacheDTO.getRequetteId());
        Tache tache = dtoMapper.fromTacheDTOtoTacheNonPlanifie(tacheDTO);
        if(requette.isPresent()){
            tache.setRequette(requette.get());
        }else {
            throw new RequetteNotFoundException();
        }
        tacheRepository.save(tache);
        return dtoMapper.fromTacheNonPlanifietoTacheDTO(tache);
    }
    @Override
    public TacheDTO modifierTache(TacheDTO tacheDTO) throws TacheNotFoundException {
        if (tacheRepository.findById(tacheDTO.getId()).isEmpty()){
            throw new TacheNotFoundException();
        }
        log.info("Planification de la tache");
        Tache tache = tacheRepository.findById(tacheDTO.getId()).get();
        tache.setIntitule(tacheDTO.getIntitule());
        tache.setObservation(tacheDTO.getObservation());
        tacheRepository.save(tache);
        return dtoMapper.fromTachetoTacheDTO(tache);
    }

    @Override
    public TacheDTO planifierTache(TacheDTO tacheDTO) throws TacheNotFoundException {
        log.info("Planification de la tache");
        if (tacheRepository.findById(tacheDTO.getId()).isEmpty()
                || collaborateurRepository.findById(tacheDTO.getCollaborateurId()).isEmpty()){
            throw new TacheNotFoundException("Tache or Collaborateur not found");
        }
        Tache tache = tacheRepository.findById(tacheDTO.getId()).get();
        Collaborateur collaborateur= collaborateurRepository.findById(tacheDTO.getCollaborateurId()).get();
        tache.setDebutPrevisionel(tacheDTO.getDebutPrevisionel());
        tache.setFinPrevisionel(tacheDTO.getFinPrevisionel());
        tache.setCollaborateur(collaborateur);
        tache.setStatusTache(StatusTache.PLANIFIE);
        tacheRepository.save(tache);
        return dtoMapper.fromTachetoTacheDTO(tache);
    }

    @Override
    public List<TacheDTO> listerTache(){
        log.info("Listing des taches");
        List<Tache> taches = tacheRepository.listeTacheOrdonnee();
        List<TacheDTO> tacheDTOS = taches.stream().map(tache -> dtoMapper
                                                    .fromTachetoTacheDTO(tache))
                                                    .collect(Collectors.toList());
        return tacheDTOS;
    }

    @Override
    public TacheDTO getOneTache(Long id) {
        Tache taches = tacheRepository.findById(id).get();
        TacheDTO tacheDTO = dtoMapper.fromTachetoTacheDTO(taches);
        if(collaborateurRepository.findCollaborateur(taches.getId()).isPresent()){
            Collaborateur collaborateur = collaborateurRepository.findCollaborateur(taches.getId()).get();
            tacheDTO.setCollaborateurId(collaborateur.getId());
            tacheDTO.setNomCollaborateur(collaborateur.getNom());
        }
        return tacheDTO;
    }

    @Override
    public List<TacheDTO> searchTacheByRequetteIdOrStatusTache(String requetteId, String statusTache) throws RequetteNotFoundException, StatusNotFoundException {
       /* if (requetteRepository.findById(tacheDTO.getRequetteId()).isEmpty()){
            throw new RequetteNotFoundException("idRequtte not found");
        }*/
        log.info("Recherche de la tache en fonction de l'id de la requette et du status de la tache");
        List<Tache> taches = tacheRepository.findTachesByRequetteOrStatus(parseLong(requetteId), StatusTache.valueOf(statusTache));
        List<TacheDTO> tachesDTO = taches.stream().map(
                            tache -> dtoMapper.fromTachetoTacheDTO(tache))
                    .collect(Collectors.toList());

        return tachesDTO;
    }


    @Override
    public void modifierStatusTache(ModifStatusTacheDTO modifStatusTacheDTO) throws TacheNotFoundException {
        log.info("Modifier le status de la tache");
        if (tacheRepository.findById(modifStatusTacheDTO.getId()).isEmpty()){
            throw new TacheNotFoundException();
        }
        Tache tache = tacheRepository.findById(modifStatusTacheDTO.getId()).get();
        tache.setStatusTache(modifStatusTacheDTO.getStatusTache());
        if (tache.getStatusTache().equals(StatusTache.EN_COURS)){
            tache.setDateDebut(new Date());
        }
        if (tache.getStatusTache().equals(StatusTache.TERMINE)){
            tache.setDateFin(new Date());
        }
        tacheRepository.save(tache);
    }
    @Override
    public void deleteTache(Long id) throws TacheNotFoundException {
        log.info("Suppression de la tache");
        if (tacheRepository.findById(id).isEmpty()){
            throw new TacheNotFoundException();
        }else {
            tacheRepository.deleteById(id);
        }
    }

    @Override
    public List<TacheDTO> searchTache(String nom) {
        List<Tache> taches = tacheRepository.searchTache(nom);
        List<TacheDTO> tacheDTOS = taches.stream().map(
                        tache -> dtoMapper.fromTachetoTacheDTO(tache))
                .collect(Collectors.toList());
        return tacheDTOS;
    }
}
