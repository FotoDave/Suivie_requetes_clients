package projet.suivie_requetes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projet.suivie_requetes.entities.FileUpload;
import projet.suivie_requetes.entities.Requette;

import java.util.List;

public interface FileUploadRepository extends JpaRepository<FileUpload, Long> {

}
