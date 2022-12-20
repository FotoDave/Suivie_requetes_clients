package projet.suivie_requetes.security.service;

import projet.suivie_requetes.exceptions.RoleNotFoundException;
import projet.suivie_requetes.exceptions.UserNotFoundException;
import projet.suivie_requetes.security.dtos.AppUserDto;
import projet.suivie_requetes.security.entities.AppRole;
import projet.suivie_requetes.security.entities.AppUser;

import java.util.ArrayList;
import java.util.List;

public interface SecurityService {
    //AppUser addNewUser(AppUser appUser);

    AppUserDto addNewUser(AppUserDto appUserDto) throws RoleNotFoundException;

    AppRole addNewRole(AppRole appRole);
    //void addRoleToUser(String userName, String roleName);

    void addRoleToUser(String userName, ArrayList<String> roleName) throws UserNotFoundException;

    AppUser loadUserByUsername(String username);
    List<AppUserDto> listUsers();

    void editUser(AppUserDto appUserDto) throws UserNotFoundException, RoleNotFoundException;
}
