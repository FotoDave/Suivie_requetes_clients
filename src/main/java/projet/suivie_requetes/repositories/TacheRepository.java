package projet.suivie_requetes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projet.suivie_requetes.entities.Tache;

import java.util.List;

public interface TacheRepository extends JpaRepository<Tache, Long> {
    @Query("select t from Tache t where t.intitule like :nom")
    List<Tache> searchTache(@Param("nom") String nom);
}
