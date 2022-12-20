package projet.suivie_requetes.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projet.suivie_requetes.exceptions.RoleNotFoundException;
import projet.suivie_requetes.exceptions.UserNotFoundException;
import projet.suivie_requetes.mappers.DtoMapper;
import projet.suivie_requetes.security.dtos.AppUserDto;
import projet.suivie_requetes.security.entities.AppRole;
import projet.suivie_requetes.security.entities.AppUser;
import projet.suivie_requetes.security.repository.AppRoleRepository;
import projet.suivie_requetes.security.repository.AppUserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService {
    private final AppRoleRepository appRoleRepository;
    private final AppUserRepository appUserRepository;
    private final DtoMapper dtoMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AppUser loadUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    @Override
    public List<AppUserDto> listUsers() {
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
    public AppUserDto addNewUser(AppUserDto appUserDto) throws RoleNotFoundException {
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
        appUserRepository.save(appUser);
        return dtoMapper.fromAppUserToAppUserDto(appUser);
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
