package projet.suivie_requetes.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projet.suivie_requetes.entities.Client;
import projet.suivie_requetes.security.entities.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser,Long> {
    AppUser findByUsername(String username);
    @Query("select a " +
            "from AppUser a " +
            "left join a.client " +
            "where a.username like :username")
    AppUser getAppUserByClient(@Param("username") String username);
}
