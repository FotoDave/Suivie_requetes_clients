package projet.suivie_requetes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projet.suivie_requetes.entities.Requette;

import java.util.List;

public interface RequetteRepository extends JpaRepository<Requette, Long> {
    @Query("select r from Requette r where r.intitule like :nom")
    List<Requette> searchRequette(@Param("nom") String nom);
}
