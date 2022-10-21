package projet.suivie_requetes.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet.suivie_requetes.dtos.RequetteDTO;
import projet.suivie_requetes.entities.Client;
import projet.suivie_requetes.entities.Requette;
import projet.suivie_requetes.exceptions.ClientNotFoundException;
import projet.suivie_requetes.exceptions.RequetteNotFoundException;
import projet.suivie_requetes.mappers.DtoMapper;
import projet.suivie_requetes.repositories.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
@RequiredArgsConstructor
public class RequetteServiceImpl implements RequetteService {
    private final ClientRepository clientRepository;
    private final CollaborateurRepository collaborateurRepository;
    private final CommentaireRepository commentaireRepository;
    private final RequetteRepository requetteRepository;
    private final TacheRepository tacheRepository;
    private final DtoMapper dtoMapper;


    @Override
    public RequetteDTO creerRequette(RequetteDTO requetteDTO) throws ClientNotFoundException {
        log.info("Creation de la requette");
        Requette requette = dtoMapper.fromRequetteDTOtoRequette(requetteDTO);
        Optional<Client> client = clientRepository.findById(requetteDTO.getClientId());
        if(!client.isPresent()){
            throw new ClientNotFoundException("ClientNotFound");
        }
        requette.setClient(client.get());
        requetteRepository.save(requette);
        return dtoMapper.fromRequettetoRequetteDTO(requette);
    }

    @Override
    public List<RequetteDTO> listerRequette(){
        log.info("Lister les requettes");
        List<Requette> requettes = requetteRepository.findAll();
        List<RequetteDTO> requetteDTOS = requettes.stream().map(requette -> dtoMapper
                .fromRequettetoRequetteDTO(requette)).collect(Collectors.toList());
        return requetteDTOS;
    }

    @Override
    public RequetteDTO getOneRequette(Long id) {
        Requette requette = requetteRepository.findById(id).get();
        RequetteDTO requetteDTO = dtoMapper.fromRequettetoRequetteDTO(requette);
        requetteDTO.setNomClient(
                clientRepository.findById(requetteDTO.getClientId()).get().getNom()
        );
        return requetteDTO;
    }

    @Override
    public List<RequetteDTO> searchRequette(String nom) {
        List<Requette> requette = requetteRepository.searchRequette(nom);
        List<RequetteDTO> requetteDTOS = requette.stream().map(
                requette1 -> dtoMapper.fromRequettetoRequetteDTO(requette1))
                .collect(Collectors.toList());
        return requetteDTOS;
    }


    @Override
    public void deleteRequette(Long id) throws RequetteNotFoundException {
        log.info("Suppression de la requette");
        if(requetteRepository.findById(id).isEmpty()){
            throw new RequetteNotFoundException("Requette absente");
        }
        requetteRepository.deleteById(id);
    }
}
