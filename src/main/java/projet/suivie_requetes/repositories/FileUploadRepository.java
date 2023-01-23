package projet.suivie_requetes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import projet.suivie_requetes.entities.FileUpload;

public interface FileUploadRepository extends JpaRepository<FileUpload, Long> {
}
