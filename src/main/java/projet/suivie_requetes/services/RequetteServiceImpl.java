package projet.suivie_requetes.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet.suivie_requetes.dtos.RequetteDTO;
import projet.suivie_requetes.ennums.StatusRequette;
import projet.suivie_requetes.ennums.TypeRequette;
import projet.suivie_requetes.entities.Client;
import projet.suivie_requetes.entities.Requette;
import projet.suivie_requetes.exceptions.ClientNotFoundException;
import projet.suivie_requetes.exceptions.RequetteNotFoundException;
import projet.suivie_requetes.exceptions.UserNotFoundException;
import projet.suivie_requetes.mappers.DtoMapper;
import projet.suivie_requetes.repositories.*;
import projet.suivie_requetes.security.entities.AppRole;
import projet.suivie_requetes.security.entities.AppUser;
import projet.suivie_requetes.security.repository.AppUserRepository;
import projet.suivie_requetes.security.service.SecurityServiceImpl;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
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
    private final AppUserRepository appUserRepository;
    private final SecurityServiceImpl securityService;
    private final DtoMapper dtoMapper;


    @Override
    public RequetteDTO creerRequette(RequetteDTO requetteDTO) throws ClientNotFoundException, UserNotFoundException {
        log.info("Creation de la requette");
        Requette requette = dtoMapper.fromRequetteDTOtoRequette(requetteDTO);
        String username = securityService.connectedUser();
        Optional<AppUser> appUserOptional = Optional.ofNullable(appUserRepository.findByUsername(username));
        if (appUserOptional.isPresent()){
            AppUser appUser = appUserOptional.get();
            Optional<Client> clientOptional = Optional.ofNullable(appUser.getClient());
            Boolean test = false;
            List<AppRole> roleList = (List<AppRole>) appUser.getAppRoles();
                for (AppRole role : roleList){
                    //ici on spécifie que l'utilisateur avec le role "Admin" peut aussi créer les requettes
                    if (role.getRoleName() == "Admin"){
                        test = true;
                    }
                }
                if (clientOptional.isPresent() || test == true){
                    //On enregistre l'utilisateur qui a créer la requette
                    requette.setAppUser(appUser);
                }
        }else{
            throw new ClientNotFoundException("Client not found or User not found...");
        }
        requette.setStatusRequette(StatusRequette.NON_TRAITE);
        requetteRepository.save(requette);
        return dtoMapper.fromRequettetoRequetteDTO(requette);
    }

    @Override
    public List<RequetteDTO> listerRequette() throws ClientNotFoundException, UserNotFoundException {
        log.info("Lister les requettes");
        String username = securityService.connectedUser();
        Optional<AppUser> appUserOptional = Optional.ofNullable(appUserRepository.getAppUserByClient(username));
        if (appUserOptional.isPresent()){
            AppUser appUser = appUserOptional.get();
            if(appUser.getClient() != null){
                if (clientRepository.findById(appUser.getClient().getId()).isPresent()){
                    //Ici, si l'utilisateur est associé à un client, on lui affiche toutes les requettes qu'il a posté
                    List<Requette> requettes = requetteRepository.listRequettesByClient(appUser.getClient().getId());
                    List<RequetteDTO> requetteDTOS = requettes.stream().map(requette -> dtoMapper
                            .fromRequettetoRequetteDTO(requette)).collect(Collectors.toList());
                    log.info("Lister les requettes uniquement par client");
                    return requetteDTOS;
                }else {
                    throw new ClientNotFoundException("Client not found at listerRequettes()...");
                }
            } else {
                //Ici, si l'utilisateur n'a pas le role "Client", on lui affiche toutes les requettes enregistrées en BD
                List<Requette> requettes = requetteRepository.listOrderRequettes();
                List<RequetteDTO> requetteDTOS = requettes.stream().map(requette -> dtoMapper
                        .fromRequettetoRequetteDTO(requette)).collect(Collectors.toList());
                log.info("Lister les requettes uniquement pour l'admin et le collaborateur");
                return requetteDTOS;
            }
          } else {
           throw new UserNotFoundException("User not found....");
        }
    }

    @Override
    public List<RequetteDTO> filterRequette(String typeRequette, String statusRequette, String intitule, Long idClient, Long id){
        log.info("Filtre des requettes");
        List<Requette> requettes = requetteRepository
                .filterRequettes(typeRequette, intitule,
                 id == null ? 0 : id, statusRequette, idClient == null ? 0 : idClient);
        List<RequetteDTO> requetteDTOS = requettes.stream().map(requette -> dtoMapper
                .fromRequettetoRequetteDTO(requette)).collect(Collectors.toList());
        return requetteDTOS;
    }

    @Override
    public RequetteDTO getOneRequette(Long id) {
        Requette requette = requetteRepository.findById(id).get();
        RequetteDTO requetteDTO = dtoMapper.fromRequettetoRequetteDTO(requette);
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
