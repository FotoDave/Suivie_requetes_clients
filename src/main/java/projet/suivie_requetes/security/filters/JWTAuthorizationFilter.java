package projet.suivie_requetes.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import projet.suivie_requetes.security.config.JWTUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@Slf4j
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals("/refreshToken")){
            filterChain.doFilter(request,response);
        }
        else{
            log.info("Verification de l'utilisateur via son AccessToken");
            String authorizationToken = request.getHeader(JWTUtil.AUTH_HEADER);
            if (authorizationToken != null && authorizationToken.startsWith(JWTUtil.PREFIX_TOKEN)){
                try {
                    String jwt = authorizationToken.substring(JWTUtil.PREFIX_TOKEN.length());
                    Algorithm algorithm = Algorithm.HMAC512(JWTUtil.SECRET);
                    JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
                    String username = decodedJWT.getSubject();
                    log.info("Utilisateur : "+username);
                    String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                    log.info("roles : "+roles);
                    Collection<GrantedAuthority> authorities = new ArrayList<>();
                    for(String r : roles){
                        authorities.add(new SimpleGrantedAuthority(r));
                    }
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(username, null, authorities);
                    //Ici, après avoir récupéré l'utilisateur et ses roles, on l'authentifie une fois.
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    //Ensuite on passe au filtre suivant
                    filterChain.doFilter(request,response);
                }
                catch (Exception e){
                    log.info("Erreur lors de la verification de l'utilisateur");
                    response.setHeader("error-message", e.getMessage());
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                }
            }
            else {
                //Sinon l'utilisateur passe quand même, mais on ne le connait pas
                filterChain.doFilter(request,response);
            }
        }
    }
}
