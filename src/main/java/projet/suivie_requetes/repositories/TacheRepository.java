package projet.suivie_requetes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import projet.suivie_requetes.entities.Tache;

public interface TacheRepository extends JpaRepository<Tache, Long> {
}
