package projet.suivie_requetes.security.service;

import projet.suivie_requetes.security.entities.AppRole;
import projet.suivie_requetes.security.entities.AppUser;

import java.util.List;

public interface SecurityService {
    AppUser addNewUser(AppUser appUser);
    AppRole addNewRole(AppRole appRole);
    void addRoleToUser(String userName, String roleName);
    AppUser loadUserByUsername(String username);
    List<AppUser> listUsers();
}
