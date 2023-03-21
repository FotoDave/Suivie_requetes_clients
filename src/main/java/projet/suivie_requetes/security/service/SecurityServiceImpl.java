package projet.suivie_requetes.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet.suivie_requetes.entities.Client;
import projet.suivie_requetes.exceptions.ClientNotFoundException;
import projet.suivie_requetes.exceptions.RoleNotFoundException;
import projet.suivie_requetes.exceptions.UserNotFoundException;
import projet.suivie_requetes.mappers.DtoMapper;
import projet.suivie_requetes.repositories.ClientRepository;
import projet.suivie_requetes.security.dtos.AppUserDto;
import projet.suivie_requetes.security.entities.AppRole;
import projet.suivie_requetes.security.entities.AppUser;
import projet.suivie_requetes.security.repository.AppRoleRepository;
import projet.suivie_requetes.security.repository.AppUserRepository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
@Slf4j
public class SecurityServiceImpl implements SecurityService {
    private final AppRoleRepository appRoleRepository;
    private final AppUserRepository appUserRepository;
    private final ClientRepository clientRepository;
    private final DtoMapper dtoMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AppUser loadUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    @Override
    public String connectedUser() throws UserNotFoundException {
        log.info("Affichage de l'utilisateur connecté");
        Optional<Authentication> authentication = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication());
        String username;
        if (authentication.isPresent()){
            Object principal = authentication.get().getPrincipal();
            if (principal instanceof String){
                username = principal.toString();
            }else {
                username = ( (AppUser) principal).getUsername();
            }
        }else {
            throw new UserNotFoundException("Utilisateur non contecté...");
        }
        return username;
    }

    @Override
    public List<AppUserDto> listUsers() {
        log.info("Liste des utilisateurs");
        List<AppUser> appUsers = appUserRepository.findAll();
        List<AppUserDto> appUserDtos = appUsers.stream().map(appUser ->
                dtoMapper.fromAppUserToAppUserDto(appUser)).collect(Collectors.toList());
        appUserDtos.forEach(appUserDto -> {
            appUsers.forEach(appUser -> {
                if (appUser.getId() == appUserDto.getId()){
                    ArrayList<String> arrayList = new ArrayList<>();
                    appUser.getAppRoles().forEach(role -> {
                        arrayList.add(role.getRoleName());
                        appUserDto.setRoles(arrayList);
                    });
                }
            });
        });
        System.out.println(appUserDtos);
        return appUserDtos;
    }

    @Override
    public AppUserDto addNewUser(AppUserDto appUserDto) throws RoleNotFoundException, ClientNotFoundException, UserNotFoundException {
        log.info("Creation de l'utilisateur");
        Optional<AppUser> appUserOptional = Optional.ofNullable(appUserRepository.findByUsername(appUserDto.getUsername()));
        if(appUserOptional.isEmpty()){
            AppUser appUser = dtoMapper.fromAppUserDtoToAppUser(appUserDto);
            String pw = appUser.getPassword();
            appUser.setPassword(passwordEncoder.encode(pw));
            for (String roleDto : appUserDto.getRoles()) {
                Optional<AppRole> appRole = Optional.ofNullable(appRoleRepository.findByRoleName(roleDto));
                if (appRole.isPresent()) {
                    AppRole role = appRole.get();
                    appUser.getAppRoles().add(role);
                } else {
                    throw new RoleNotFoundException("Role not found !!");
                }
            }
            if (appUserDto.getClientId() != null){
                Optional<Client> optionalClient = clientRepository.findById(appUserDto.getClientId());
                if (optionalClient.isPresent()){
                    appUser.setClient(optionalClient.get());
                }else {
                    throw new ClientNotFoundException("Client not found...");
                }
            }
            appUserRepository.save(appUser);
            return dtoMapper.fromAppUserToAppUserDto(appUser);

        }else {
            throw new UserNotFoundException("User already exist...");
        }

    }

    @Override
    public void editUser(AppUserDto appUserDto) throws UserNotFoundException, RoleNotFoundException {
        Optional<AppUser> appUser = appUserRepository.findById(appUserDto.getId());
        if (appUser.isPresent()){
            AppUser user = appUser.get();
            user.setUsername(appUserDto.getUsername());
            user.getAppRoles().clear();
            for (String roleDto : appUserDto.getRoles()) {
                Optional<AppRole> appRole = Optional.ofNullable(appRoleRepository.findByRoleName(roleDto));
                if (appRole.isPresent()) {
                    AppRole role = appRole.get();
                    user.getAppRoles().add(role);
                } else {
                    throw new RoleNotFoundException("Role not found !!");
                }
            }
            appUserRepository.save(user);
        } else {
            throw new UserNotFoundException("User or Role not found !");
        }
    }

    @Override
    public AppRole addNewRole(AppRole appRole) {
        return appRoleRepository.save(appRole);
    }

    @Override
    public void addRoleToUser(String userName, ArrayList<String> roleName) throws UserNotFoundException {
        Optional<AppUser> appUser = Optional.ofNullable(appUserRepository.findByUsername(userName));
        if (appUser.isPresent()){
            AppUser user = appUser.get();
            /*roleName.forEach(role -> {
                AppRole appRole = appRoleRepository.findByRoleName(role);
                user.getAppRoles().add(appRole);
            } );*/
            appUserRepository.save(user);
        } else {
            throw new UserNotFoundException("User not found !");
        }
    }

}
