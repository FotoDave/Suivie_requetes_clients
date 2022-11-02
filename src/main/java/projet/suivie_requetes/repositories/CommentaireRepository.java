package projet.suivie_requetes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projet.suivie_requetes.entities.Commentaire;

import java.util.List;

public interface CommentaireRepository extends JpaRepository<Commentaire, Long> {
    @Query("select c from Commentaire c where c.tache.id = :id")
    List<Commentaire> findCommentairesByTache(@Param("id") Long id);
}
