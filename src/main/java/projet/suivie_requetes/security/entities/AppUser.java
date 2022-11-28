package projet.suivie_requetes.security.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class AppUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    /*Ceci permet au mot de passe de ne pas être visible lors de la sérialisation
    dans l'objet json en lecture au niveau de l'API */
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    //Lorsqu'on travail avec "EAGER", il faut toujours instancier la ArrayList.
    private Collection<AppRole> appRoles = new ArrayList<>();
}
