package projet.suivie_requetes.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projet.suivie_requetes.security.entities.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser,Long> {
    AppUser findByUsername(String username);
}
