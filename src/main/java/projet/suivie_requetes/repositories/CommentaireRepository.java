package projet.suivie_requetes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import projet.suivie_requetes.entities.Commentaire;

public interface CommentaireRepository extends JpaRepository<Commentaire, Long> {
}
