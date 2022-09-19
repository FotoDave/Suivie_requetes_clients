package projet.suivie_requetes.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet.suivie_requetes.dtos.ModifStatusTacheDTO;
import projet.suivie_requetes.dtos.PlanifierTacheDTO;
import projet.suivie_requetes.dtos.TacheDTO;
import projet.suivie_requetes.ennums.StatusTache;
import projet.suivie_requetes.entities.Collaborateur;
import projet.suivie_requetes.entities.Requette;
import projet.suivie_requetes.entities.Tache;
import projet.suivie_requetes.exceptions.RequetteNotFoundException;
import projet.suivie_requetes.exceptions.TacheAlreadyExistException;
import projet.suivie_requetes.exceptions.TacheNotFoundException;
import projet.suivie_requetes.mappers.DtoMapper;
import projet.suivie_requetes.repositories.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(noRollbackFor = Exception.class)
@Slf4j
public class TacheServiceImpl implements TacheService {
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
    public TacheDTO creerTache(TacheDTO tacheDTO) throws RequetteNotFoundException, TacheAlreadyExistException {
        if (tacheRepository.findById(tacheDTO.getId()).isPresent()){
            throw new TacheAlreadyExistException("La tache existe deja");
        }
        log.info("Creation Requette");
        tacheDTO.setStatusTache(StatusTache.NON_PLANIFIE);
        Tache tache = dtoMapper.fromTacheDTOtoTache(tacheDTO);
        Optional<Collaborateur> collaborateur = collaborateurRepository.findById(tacheDTO.getCollaborateurId());
        Optional<Requette> requette = requetteRepository.findById(tacheDTO.getRequetteId());
        if(collaborateur.isEmpty() || requette.isEmpty()){
            throw new RequetteNotFoundException("Requette ou Collaborateur not found");
        }else{
            tache.setCollaborateur(collaborateur.get());
            tache.setRequette(requette.get());
            tacheRepository.save(tache);
            return dtoMapper.fromTachetoTacheDTO(tache);
        }
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
    public List<TacheDTO> listerTache(){
        log.info("Listing des taches");
        List<Tache> taches = tacheRepository.findAll();
        List<TacheDTO> tacheDTOS = taches.stream().map(tache -> dtoMapper
                                                    .fromTachetoTacheDTO(tache))
                                                    .collect(Collectors.toList());
        return tacheDTOS;
    }

    @Override
    public TacheDTO planifierTache(PlanifierTacheDTO planifierTacheDTO) throws TacheNotFoundException {
        log.info("Planification de la tache");
        if (tacheRepository.findById(planifierTacheDTO.getTacheId()).isEmpty()
                || collaborateurRepository.findById(planifierTacheDTO.getCollaborateurId()).isEmpty()){
            throw new TacheNotFoundException("Tache or Collaborateur not found");
        }
        Tache tache = tacheRepository.findById(planifierTacheDTO.getTacheId()).get();
        Collaborateur collaborateur= collaborateurRepository.findById(planifierTacheDTO.getCollaborateurId()).get();
        tache.setDebutPrevisionel(planifierTacheDTO.getDateDebutPrev());
        tache.setFinPrevisionel(planifierTacheDTO.getDateFinPrev());
        tache.setCollaborateur(collaborateur);
        tache.setStatusTache(StatusTache.PLANIFIE);

        tacheRepository.save(tache);
        
        return dtoMapper.fromTachetoTacheDTO(tache);
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

}
