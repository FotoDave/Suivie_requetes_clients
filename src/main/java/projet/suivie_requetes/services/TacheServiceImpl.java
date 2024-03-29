package projet.suivie_requetes.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet.suivie_requetes.dtos.TacheDTO;
import projet.suivie_requetes.ennums.StatusRequette;
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
import projet.suivie_requetes.utils.FunctionUtils;

import java.util.ArrayList;
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
        Optional<Requette> requetteOptional = requetteRepository.findById(tacheDTO.getRequetteId());
        Tache tache = dtoMapper.fromTacheDTOtoTacheNonPlanifie(tacheDTO);
        if(requetteOptional.isPresent()){
            //On change le statut de la requette à EN_COURS
            Requette requette = requetteOptional.get();
            if (requette.getStatusRequette() == StatusRequette.TRAITE){
                throw new RequetteNotFoundException("La requette est déjà traité");
            }
            if (requette.getStatusRequette() == StatusRequette.NON_TRAITE){
                requette.setStatusRequette(StatusRequette.EN_COURS);
                requetteRepository.save(requette);
            }
            tache.setRequette(requette);
        }else {
            throw new RequetteNotFoundException();
        }
        tacheRepository.save(tache);
        return dtoMapper.fromTachetoTacheDTO(tache);
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
    public TacheDTO modifierStatutTache(TacheDTO tacheDTO) throws TacheNotFoundException, RequetteNotFoundException {
        log.info("Changement du statut de la tache");
        if (tacheRepository.findById(tacheDTO.getId()).isEmpty()){
            throw new TacheNotFoundException("Tache not found...");
        }
        Tache tache = tacheRepository.findById(tacheDTO.getId()).get();
        switch (tacheDTO.getStatusTache()){
            case EN_COURS -> {
                tache.setDateDebut(new Date());
                break;
            }
            case TERMINE -> {
                tache.setDateFin(new Date());
                break;
            }
            case DEPLOYE -> {
                log.info("Changement du status à 'DEPLOYE'");
                Optional<Requette> requetteOptional = requetteRepository.findById(tacheDTO.getRequetteId());
                if (requetteOptional.isPresent()){
                    List<Tache> taches = tacheRepository.findTachesByRequette(tacheDTO.getRequetteId());
                    Boolean test = false;
                    ArrayList<String> arrayList = new ArrayList<>();
                    for (Tache tache1 : taches){
                        if (tache1.getStatusTache() == StatusTache.DEPLOYE && tache1.getId() != tache.getId()){
                            arrayList.add("Okay");
                        }
                    }
                    if (arrayList.size() == (taches.size()-1)){
                        /*
                        Si la taille du tableau de taches est la même que celle de l'arraylist,
                         je change le statut de la requette à "TRAITE"
                         */
                        Requette requette = requetteOptional.get();
                        requette.setStatusRequette(StatusRequette.TRAITE);
                        requetteRepository.save(requette);
                    }
                }else {
                    throw new RequetteNotFoundException("Requette not found...");
                }
                break;
            }
        }
        tache.setStatusTache(tacheDTO.getStatusTache());
        tacheRepository.save(tache);
        return dtoMapper.fromTachetoTacheDTO(tache);
    }

    @Override
    public List<TacheDTO> filterTaches(Long idTache, Long idReq,
                                       Long idCollab, String statut/*,
                                       Date dateDebut, Date dateFin,
                                       Date dateDebutPrev, Date dateFinPrev*/){
        List<Tache> taches = tacheRepository.filterTache(idTache == null ? 0 : idTache, idReq == null ? 0 : idReq,
                idCollab == null ? 0 : idCollab, statut/*,
                dateDebut == null ? null : dateDebut,
                dateFin == null ? null : dateFin,
                dateDebutPrev == null ? null : dateDebutPrev,
                dateFinPrev == null ? null : dateFinPrev*/);
        List<TacheDTO> tacheDTOS = taches.stream().map(tache ->
                dtoMapper.fromTachetoTacheDTO(tache)).collect(Collectors.toList());

        return tacheDTOS;
    }

    @Override
    public List<TacheDTO> listerTache(){
        log.info("Listing des taches");
        List<Tache> taches = tacheRepository.listeTacheOrdonnee();
        List<TacheDTO> tacheDTOS = taches.stream().map(tache ->
                dtoMapper.fromTachetoTacheDTO(tache)).collect(Collectors.toList());
        return tacheDTOS;
    }

    @Override
    public TacheDTO getOneTache(Long id) throws TacheNotFoundException {
        if (tacheRepository.findById(id).isEmpty()){
            throw new TacheNotFoundException("Tache not found...");
        }
        Tache tache = tacheRepository.getTacheByRequette(id);
        Optional<Tache> tacheOptional = Optional.ofNullable(tacheRepository.getTacheByCollaborateur(id));
        if (tacheOptional.isPresent()){
            Tache tache1 = tacheOptional.get();
            tache.setCollaborateur(tache1.getCollaborateur());
        }
        TacheDTO tacheDTO = dtoMapper.fromTachetoTacheDTO(tache);

        return tacheDTO;
    }
}
