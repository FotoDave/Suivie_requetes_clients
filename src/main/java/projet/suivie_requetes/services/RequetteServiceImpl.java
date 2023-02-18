package projet.suivie_requetes.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet.suivie_requetes.dtos.RequetteDTO;
import projet.suivie_requetes.ennums.StatusRequette;
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
    private final AppUserRepository appUserRepository;
    private final SecurityServiceImpl securityService;
    private final DtoMapper dtoMapper;


    @Override
    public RequetteDTO creerRequette(RequetteDTO requetteDTO) throws ClientNotFoundException {
        log.info("Creation de la requette");
        Requette requette = dtoMapper.fromRequetteDTOtoRequette(requetteDTO);
        Optional<AppUser> appUserOptional = Optional.ofNullable(appUserRepository.findByUsername(requetteDTO.getUsername()));
        if (appUserOptional.isPresent()){
            AppUser appUser = appUserOptional.get();
            Optional<Client> clientOptional = clientRepository.findById(appUser.getClient().getId());
            Boolean test = false;
            List<AppRole> roleList = (List<AppRole>) appUser.getAppRoles();
            for (AppRole role : roleList){
                if (role.getRoleName() == "Admin"){
                    test = true;
                }
            }
            if (clientOptional.isPresent() || test == true){
                requette.setAppUser(appUser);
            }
        }else{
            throw new ClientNotFoundException("Client not found or Utilisateur not found...");
        }
        requette.setStatusRequette(StatusRequette.NON_TRAITE);
        requetteRepository.save(requette);
        return dtoMapper.fromRequettetoRequetteDTO(requette);
    }

    @Override
    public List<RequetteDTO> listerRequette() throws ClientNotFoundException, UserNotFoundException {
        log.info("Lister les requettes uniquement par client");
        String username = securityService.connectedUser();
        Optional<AppUser> appUserOptional = Optional.ofNullable(securityService.loadUserByUsername(username));
        if (appUserOptional.isPresent()){
            AppUser appUser = appUserOptional.get();
            Optional<Client> clientOptional = clientRepository.findById(appUser.getClient().getId());
            Boolean test = false;
            List<AppRole> roleList = (List<AppRole>) appUser.getAppRoles();
            for (AppRole role : roleList){
                if (role.getRoleName() == "Admin"){
                    test = true;
                }
            }
            if (clientOptional.isPresent()){
                List<Requette> requettes = requetteRepository.listRequettesByClient(appUser.getClient().getId());
                List<RequetteDTO> requetteDTOS = requettes.stream().map(requette -> dtoMapper
                        .fromRequettetoRequetteDTO(requette)).collect(Collectors.toList());
                return requetteDTOS;
            }else if (test == true){
                List<Requette> requettes = requetteRepository.listOrderRequettes();
                List<RequetteDTO> requetteDTOS = requettes.stream().map(requette -> dtoMapper
                        .fromRequettetoRequetteDTO(requette)).collect(Collectors.toList());
                return requetteDTOS;
            }else {
                throw new ClientNotFoundException("Client not found...");
            }
        } else {
           throw new UserNotFoundException("User not found....");
        }
    }

    @Override
    public RequetteDTO getOneRequette(Long id) {
        Requette requette = requetteRepository.findById(id).get();
        RequetteDTO requetteDTO = dtoMapper.fromRequettetoRequetteDTO(requette);
        /*requetteDTO.setNomClient(
                clientRepository.findById(requetteDTO.getClientId()).get().getNom()
        );*/
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
