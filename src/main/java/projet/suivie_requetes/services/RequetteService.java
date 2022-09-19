package projet.suivie_requetes.services;

import projet.suivie_requetes.dtos.RequetteDTO;
import projet.suivie_requetes.exceptions.ClientNotFoundException;
import projet.suivie_requetes.exceptions.RequetteNotFoundException;

import java.util.List;

public interface RequetteService {
    RequetteDTO creerRequette(RequetteDTO requetteDTO) throws ClientNotFoundException;

    List<RequetteDTO> listerRequette();

    void deleteRequette(Long id) throws RequetteNotFoundException;
}
