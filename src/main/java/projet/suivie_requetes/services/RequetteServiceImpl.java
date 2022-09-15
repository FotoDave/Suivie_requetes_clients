package projet.suivie_requetes.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet.suivie_requetes.dtos.RequetteDTO;
import projet.suivie_requetes.entities.Requette;
import projet.suivie_requetes.mappers.DtoMapper;
import projet.suivie_requetes.repositories.*;

@Service
@Transactional
@Slf4j
public class RequetteServiceImpl implements RequetteService {
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
    public RequetteDTO creerRequette(RequetteDTO requetteDTO) {
        log.info("Creation de la requette");
        Requette requette = dtoMapper.fromRequetteDTOtoRequette(requetteDTO);
        requetteRepository.save(requette);
        return dtoMapper.fromRequettetoRequetteDTO(requette);
    }
}
