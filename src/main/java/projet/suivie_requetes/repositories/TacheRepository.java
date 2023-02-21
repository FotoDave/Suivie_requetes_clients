package projet.suivie_requetes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projet.suivie_requetes.dtos.TacheDTO;
import projet.suivie_requetes.ennums.StatusTache;
import projet.suivie_requetes.entities.Collaborateur;
import projet.suivie_requetes.entities.Tache;

import java.util.Date;
import java.util.List;

public interface TacheRepository extends JpaRepository<Tache, Long> {
    @Query("select t " +
            "from Tache t " +
            "where t.intitule " +
            "like :nom")
    List<Tache> searchTache(@Param("nom") String nom);

    @Query("select t " +
            "from Tache t " +
            "left join t.requette r " +
            "where t.id = :id")
    Tache getTacheByRequette(@Param("id") Long id);
    @Query("select t " +
            "from Tache t " +
            "left join t.collaborateur r " +
            "where t.id = :id")
    Tache getTacheByCollaborateur(@Param("id") Long id);

    @Query("select t from Tache t " +
            "left join t.requette req " +
            "where req.id = :id and t.statusTache = :status " +
            "order by t.dateCreation desc")
    List<Tache> findTachesByRequetteOrStatus(@Param("id") Long id, @Param("status") StatusTache status);

    @Query("select t " +
            "from Tache t " +
            "left join t.requette req " +
            "left join t.collaborateur collab " +
            "where :idCollab is null or :idCollab = '' or collab.id = :idCollab " +
            "and :id is null or :id = '' or t.id = :id " +
            "and :idReq is null or :idReq = '' or req.id = :idReq " +
            "and :statut is null or :statut = '' or t.statusTache = :statut " +
            "and :dateDebut is null or :dateDebut = '' or t.dateDebut = :dateDebut " +
            "and :dateFin is null or :dateFin = '' or t.dateFin = :dateFin " +
            "and :dateDebutPrev is null or :dateDebutPrev = '' or t.debutPrevisionel = :dateDebutPrev " +
            "and :dateFinPrev is null or :dateFinPrev = '' or t.finPrevisionel = :dateFinPrev ")
    List<Tache> filterTache(@Param("id") Long idTache, @Param("idReq") Long idReq,
                            @Param("idCollab") Long idCollab, @Param("statut") String statut,
                            @Param("dateDebut") Date dateDebut, @Param("dateFin") Date dateFin,
                            @Param("dateDebutPrev") Date dateDebutPrev, @Param("dateFinPrev") Date dateFinPrev);

    /*@Query("select new projet.suivie_requetes.dtos.TacheDTO(" +
            "t.id," +
            "t.intitule, " +
            "t.dateDebut, " +
            "t.dateFin, " +
            "t.observation, " +
            "t.debutPrevisionel, " +
            "t.finPrevisionel, " +
            "t.statusTache, " +
            "t.dateCreation," +
            "req.id, " +
            "col.id," +
            "col.nom) " +
            "from Tache t " +
            "left join t.requette req " +
            "left join t.collaborateur col " +
            "order by t.dateCreation desc ")
    List<TacheDTO> listeTacheOrdonnee();*/

    @Query("select t " +
            "from Tache t " +
            "order by t.dateCreation desc ")
    List<Tache> listeTacheOrdonnee();

    @Query("select t " +
            "from Tache t " +
            "left join Requette r " +
            "where r.id = :id")
    List<Tache> findTachesByRequette(@Param("id") Long id);
    /*List<Tache> findAllByRequetteIdOrStatusTache(@Param("id") Long id, @Param("status") String status);*/
}
