package projet.suivie_requetes.services.requetteService;

import projet.suivie_requetes.dtos.RequetteDTO;
import projet.suivie_requetes.entities.Requette;
import projet.suivie_requetes.exceptions.ClientNotFoundException;
import projet.suivie_requetes.exceptions.RequetteNotFoundException;

import java.util.List;

public interface RequetteService {
    RequetteDTO creerRequette(RequetteDTO requetteDTO) throws ClientNotFoundException;

    List<RequetteDTO> listerRequette();
    RequetteDTO getOneRequette(Long id);
    List<RequetteDTO> searchRequette(String nom);

    void deleteRequette(Long id) throws RequetteNotFoundException;
}
