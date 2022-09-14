package projet.suivie_requetes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import projet.suivie_requetes.entities.Requette;

public interface RequetteRepository extends JpaRepository<Requette, Long> {
}
