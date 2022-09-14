package projet.suivie_requetes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import projet.suivie_requetes.entities.Collaborateur;

public interface CollaborateurRepository extends JpaRepository<Collaborateur, Long> {
}
