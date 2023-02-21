package projet.suivie_requetes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projet.suivie_requetes.entities.FileUpload;

import java.util.List;
import java.util.Optional;

public interface FileUploadRepository extends JpaRepository<FileUpload, Long> {
    @Query("select f " +
            "from FileUpload f " +
            "left join f.requette req " +
            "where req.id = :id")
    Optional<FileUpload> findFileUploadByRequetteId(@Param("id") Long id);
    @Query("select f " +
            "from FileUpload f " +
            "left join f.tache tache " +
            "where tache.id = :id")
    Optional<FileUpload> findFileUploadByTacheId(@Param("id") Long id);

    @Query("select f " +
            "from FileUpload f " +
            "left join f.commentaire com " +
            "where com.id = :id")
    List<FileUpload> findFileUploadByCommentaireId(@Param("id") Long id);
}
