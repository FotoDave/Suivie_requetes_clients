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
import projet.suivie_requetes.security.config.JWTUtil;

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
public class JWTAuthenticationFIlter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                         HttpServletResponse response) throws AuthenticationException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        log.info("Attemm Authentication !!!");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        log.info("Récupération de l'utilisateur et du password");
        log.info(username);
        //Ici on stocke les infos de l'utilisateur dans la classe UsernamePasswordAuthentificationToken
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username,password);

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                    FilterChain chain, Authentication authResult) throws IOException, ServletException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");
        log.info("Succesful Authentication !!!");
        /* Après l'authentification, on récupère les infos de l'utilisateur
        dans l'objet (User) pour pouvoir générer le token */
        User user = (User) authResult.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC512(JWTUtil.SECRET);
        String jwtAccessToken = JWT.create()
                .withSubject(user.getUsername())
                //date d'expiration du token
                .withExpiresAt(new Date(System.currentTimeMillis()+JWTUtil.EXPIRED_ACCESS_TOKEN))
                //nom de l'application qui a généré le token
                .withIssuer(request.getRequestURL().toString())
                //role des utilisateurs
                .withClaim("roles", user.getAuthorities()
                                                .stream()
                                                .map(grantedAuthority -> grantedAuthority.getAuthority())
                                                .collect(Collectors.toList()))
                //signature
                .sign(algorithm);

        String jwtRefreshToken = JWT.create()
                .withSubject(user.getUsername())
                //date d'expiration du token
                .withExpiresAt(new Date(System.currentTimeMillis()+JWTUtil.EXPIRED_REFRESH_TOKEN))
                //nom de l'application qui a généré le token
                .withIssuer(request.getRequestURL().toString())
                //signature
                .sign(algorithm);

        /* Ici on crée une hashmap dans laquelle on insère les deux tokens,
        ensuite on la sérialise pour l'envoyer dans le corps de la réponse sous format JSON */
        Map<String,String> idToken = new HashMap<>();
        idToken.put("acces_token", jwtAccessToken);
        idToken.put("refresh_token", jwtRefreshToken);
        idToken.put("username", (String) user.getUsername());
        response.setContentType("application/json");
        //Serialisation du format json pour l'envoie des tokens dans la reponse
        new ObjectMapper().writeValue(response.getOutputStream(), idToken);
            //response.setHeader("Authorization", jwtAccessToken);
    }
}
