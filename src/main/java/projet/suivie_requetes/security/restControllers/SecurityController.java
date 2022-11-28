package projet.suivie_requetes.security.restControllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import projet.suivie_requetes.security.dtos.RolesToUserDto;
import projet.suivie_requetes.security.entities.AppRole;
import projet.suivie_requetes.security.entities.AppUser;
import projet.suivie_requetes.security.service.SecurityServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class SecurityController {
    private final SecurityServiceImpl securityService;

    @GetMapping("/users")
    public List<AppUser> ListlistUsers(){
        log.info("Affichage de la liste des utilisateurs");
        return securityService.listUsers();
    }

    @PostMapping("/users")
    public AppUser saveUser(@RequestBody AppUser appUser){
        log.info("Creation de l'utilisateur de l'utilisateur");
        return securityService.addNewUser(appUser);
    }

    @PostMapping("/roles")
    public AppRole saveRole(@RequestBody AppRole appRole){
        log.info("Creation de l'utilisateur de l'utilisateur");
        return securityService.addNewRole(appRole);
    }

    @PostMapping("/roleToUser")
    public void addRoleToUser(@RequestBody RolesToUserDto rolesToUserDto){
        log.info("Ajout du role à l'utilisateur");
        securityService.addRoleToUser(rolesToUserDto.getUsername(), rolesToUserDto.getRoleName());
    }
}
