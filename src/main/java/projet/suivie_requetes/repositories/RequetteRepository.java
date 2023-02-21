package projet.suivie_requetes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projet.suivie_requetes.entities.Requette;

import java.util.List;

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

    @Query("select r " +
            "from Requette r " +
            "left join r.appUser a " +
            "left join a.client c " +
            "where :idReq is null or :idReq = '' or r.id = :idReq " +
            "and :type is null or :type = '' or r.typeRequette = :type " +
            "and :intitule is null or :intitule = '' or r.intitule like :intitule " +
            "and :urgence is null or :urgence = '' or r.urgence like :urgence " +
            "and :obser is null or :obser = '' or r.observation like :obser " +
            "and :module is null or :module = '' or r.module like :module " +
            "and :idClient is null or :idClient = '' or c.id = :idClient " +
            "order by r.date_creation desc ")
    List<Requette> filterRequettes(@Param("type") String type, @Param("intitule") String intitule,
                                   @Param("module") String module, @Param("urgence") String urgence,
                                   @Param("obser") String observation, @Param("idReq") Long idReq,
                                   @Param("idClient") Long idClient);

}
