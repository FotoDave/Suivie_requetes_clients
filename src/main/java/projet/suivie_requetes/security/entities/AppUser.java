package projet.suivie_requetes.security.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import projet.suivie_requetes.entities.Client;
import projet.suivie_requetes.entities.Requette;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class AppUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    /*Ceci permet au mot de passe de ne pas être visible lors de la sérialisation
    dans l'objet json en lecture au niveau de l'API */
    private String password;

    @OneToMany(mappedBy = "appUser")
    private List<Requette> requetteList;

    @ManyToOne
    @JoinColumn(name = "utilisateur.client_id")
    private Client client;
    @ManyToMany(fetch = FetchType.EAGER)
    //Lorsqu'on travail avec "EAGER", il faut toujours instancier la ArrayList.
    private Collection<AppRole> appRoles = new ArrayList<>();
}
