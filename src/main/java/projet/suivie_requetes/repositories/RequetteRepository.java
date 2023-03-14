package projet.suivie_requetes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projet.suivie_requetes.ennums.StatusRequette;
import projet.suivie_requetes.ennums.TypeRequette;
import projet.suivie_requetes.entities.Requette;

import java.util.List;
@Repository
public interface RequetteRepository extends JpaRepository<Requette, Long> {
    @Query("select r " +
            "from Requette r " +
            "where r.intitule like :nom " +
            "order by r.date_creation desc ")
    List<Requette> searchRequette(@Param("nom") String nom);

    @Query("select r " +
            "from Requette r " +
            "left join r.appUser a " +
            "left join a.client c " +
            "where c.id = :id " +
            "order by r.date_creation desc ")
    List<Requette> listRequettesByClient(@Param("id") Long id);

    @Query("select r " +
            "from Requette r " +
            "order by r.date_creation desc ")
    List<Requette> listOrderRequettes();

    @Query(
            value = "SELECT * FROM requette " +
                    "LEFT JOIN app_user ON requette.requette_app_user_id = app_user.id " +
                    "WHERE (:idReq = 0 OR requette.id = :idReq) " +
                    "AND (:intitule IS NULL OR :intitule = '' OR requette.intitule = :intitule) " +
                    "AND (:type IS NULL OR :type = '' OR requette.type_requette = :type) " +
                    "AND (:status IS NULL OR :status = '' OR requette.status_requette = :status) " +
                    "AND (:idClient = 0 OR app_user.utilisateur_client_id = :idClient) " +
                    "ORDER BY requette.date_creation DESC",
            nativeQuery = true
    )
    List<Requette> filterRequettes(@Param("type") String type, @Param("intitule") String intitule,
                                   @Param("idReq") Long idReq, @Param("status") String status,
                                   @Param("idClient") Long idClient);

}
