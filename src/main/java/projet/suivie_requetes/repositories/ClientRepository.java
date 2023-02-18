package projet.suivie_requetes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import projet.suivie_requetes.entities.Client;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query("select c from Client c where c.nom like :kw")
    List<Client> searchClient(@Param("kw") String keyword);

    /*@Query("select c " +
            "from Client c " +
            "left join c.appUser a " +
            "where a.id = :id")
    Client getClientByAppUser(@Param("id") Long id);*/
}
