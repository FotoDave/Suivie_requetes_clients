package projet.suivie_requetes.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class JWTAuthentififcationFIlter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                         HttpServletResponse response) throws AuthenticationException {
        log.info("Attemm Authentication !!!");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        log.info("Récupération de l'utilisateur et du password");
        log.info(username);
        log.info(password);
        //Ici on stocke les infos de l'utilisateur dans la classe UsernamePasswordAuthentificationToken
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username,password);

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                    FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("Succesful Authentication !!!");
        /* Après l'authentification, on récupère les infos de l'utilisateur
        dans l'objet (User) pour pouvoir générer le token */
        User user = (User) authResult.getPrincipal();
        Algorithm algorithm1 = Algorithm.HMAC512("Foto237*");
        String jwtAccessToken = JWT.create()
                .withSubject(user.getUsername())
                //date d'expiration du token
                .withExpiresAt(new Date(System.currentTimeMillis()+5*60*1000))
                //nom de l'application qui a généré le token
                .withIssuer(request.getRequestURL().toString())
                //role des utilisateurs
                .withClaim("roles", user.getAuthorities()
                                                .stream()
                                                .map(grantedAuthority -> grantedAuthority.getAuthority())
                                                .collect(Collectors.toList()))
                //signature
                .sign(algorithm1);

        String jwtRefreshToken = JWT.create()
                .withSubject(user.getUsername())
                //date d'expiration du token
                .withExpiresAt(new Date(System.currentTimeMillis()+15*60*1000))
                //nom de l'application qui a généré le token
                .withIssuer(request.getRequestURL().toString())
                //signature
                .sign(algorithm1);

        /* Ici on crée une hashmap dans laquelle on insère les deux tokens,
        ensuite on la sérialise pour l'envoyer dans le corps de la réponse sous format JSON */
        Map<String,String> idToken = new HashMap<>();
        idToken.put("acces-token", jwtAccessToken);
        idToken.put("refresh-token", jwtRefreshToken);
        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getOutputStream(), idToken);
        //Envoie du token dans le header
        response.setHeader("Authorisation", jwtAccessToken);
    }
}
