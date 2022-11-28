package projet.suivie_requetes.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projet.suivie_requetes.security.entities.AppRole;

public interface AppRoleRepository extends JpaRepository<AppRole,Long> {
    AppRole findByRoleName(String roleName);
}
