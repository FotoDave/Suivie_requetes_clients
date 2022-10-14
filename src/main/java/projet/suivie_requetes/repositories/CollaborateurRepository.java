package projet.suivie_requetes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projet.suivie_requetes.entities.Collaborateur;

import java.util.List;

public interface CollaborateurRepository extends JpaRepository<Collaborateur, Long> {
    @Query("select c from Collaborateur c where c.nom like :nom")
    List<Collaborateur> searchCollaborateur(@Param("nom") String nom);
}
