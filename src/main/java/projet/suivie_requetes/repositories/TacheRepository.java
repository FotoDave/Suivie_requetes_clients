package projet.suivie_requetes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projet.suivie_requetes.ennums.StatusTache;
import projet.suivie_requetes.entities.Collaborateur;
import projet.suivie_requetes.entities.Tache;

import java.util.List;

public interface TacheRepository extends JpaRepository<Tache, Long> {
    @Query("select t " +
            "from Tache t " +
            "where t.intitule " +
            "like :nom")
    List<Tache> searchTache(@Param("nom") String nom);

    @Query("select t from Tache t " +
            "left join t.requette req " +
            "where req.id = :id and t.statusTache = :status " +
            "order by t.dateCreation desc")
    List<Tache> findTachesByRequetteOrStatus(@Param("id") Long id, @Param("status") StatusTache status);

    @Query("select t " +
            "from Tache t " +
            "order by t.dateCreation desc")
    List<Tache> listeTacheOrdonnee();
    /*List<Tache> findAllByRequetteIdOrStatusTache(@Param("id") Long id, @Param("status") String status);*/
}
