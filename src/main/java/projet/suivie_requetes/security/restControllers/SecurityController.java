package projet.suivie_requetes.security.restControllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import projet.suivie_requetes.security.config.JWTUtil;
import projet.suivie_requetes.security.dtos.RolesToUserDto;
import projet.suivie_requetes.security.entities.AppRole;
import projet.suivie_requetes.security.entities.AppUser;
import projet.suivie_requetes.security.service.SecurityServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class SecurityController {
    private final SecurityServiceImpl securityService;

    @GetMapping("/users")
    @PostAuthorize("hasAuthority('Admin')")
    public List<AppUser> ListlistUsers(){
        log.info("Affichage de la liste des utilisateurs");
        return securityService.listUsers();
    }

    @PostMapping("/users")
    @PostAuthorize("hasAuthority('Admin')")
    public AppUser saveUser(@RequestBody AppUser appUser){
        log.info("Creation de l'utilisateur de l'utilisateur");
        return securityService.addNewUser(appUser);
    }

    @PostMapping("/roles")
    @PostAuthorize("hasAuthority('Admin')")
    public AppRole saveRole(@RequestBody AppRole appRole){
        log.info("Creation de l'utilisateur de l'utilisateur");
        return securityService.addNewRole(appRole);
    }

    @PostMapping("/roleToUser")
    @PostAuthorize("hasAuthority('Admin')")
    public void addRoleToUser(@RequestBody RolesToUserDto rolesToUserDto){
        log.info("Ajout du role à l'utilisateur");
        securityService.addRoleToUser(rolesToUserDto.getUsername(), rolesToUserDto.getRoleName());
    }

    @GetMapping("/profile")
    public AppUser profile(Principal principal){
        return securityService.loadUserByUsername(principal.getName());
    }

    @GetMapping("/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String authToken = request.getHeader(JWTUtil.AUTH_HEADER);
        if (authToken != null && authToken.startsWith(JWTUtil.PREFIX_TOKEN)){
            try {
                String jwt = authToken.substring(JWTUtil.PREFIX_TOKEN.length());
                Algorithm algorithm = Algorithm.HMAC512(JWTUtil.SECRET);
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
                String username = decodedJWT.getSubject();
                AppUser appUser = securityService.loadUserByUsername(username);
                String jwtAccessToken = JWT.create()
                        .withSubject(appUser.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis()+JWTUtil.EXPIRED_ACCESS_TOKEN))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", appUser.getAppRoles()
                                .stream()
                                .map(r -> r.getRoleName())
                                .collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String,String> idToken = new HashMap<>();
                idToken.put("acces_token", jwtAccessToken);
                idToken.put("refresh_token", jwt);
                idToken.put("username", appUser.getUsername());
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(), idToken);
            }
            catch (Exception e){
                log.info("Erreur lors de la verification de l'utilisateur");
                throw e;
            }
        }
        else {
            throw new RuntimeException("Refresh token required!!!");
        }
    }
}
