package projet.suivie_requetes.mappers;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import projet.suivie_requetes.dtos.*;
import projet.suivie_requetes.entities.*;

@Service
public class DtoMapper {
    public ClientDTO fromClienttoClientDTO(Client client){
        ClientDTO clientDTO = new ClientDTO();
        BeanUtils.copyProperties(client, clientDTO);
        return clientDTO;
    }
    public Client fromClientDTOtoClient(ClientDTO clientDTO){
        Client client = new Client();
        BeanUtils.copyProperties(clientDTO, client);
        return client;
    }

    public CollaborateurDTO fromCollaborateurtoCollaborateurDTO(Collaborateur collaborateur){
        CollaborateurDTO collaborateurDTO = new CollaborateurDTO();
        BeanUtils.copyProperties(collaborateur, collaborateurDTO);
        return collaborateurDTO;
    }
    public Collaborateur fromCollaborateurDTOtoCollaborateur(CollaborateurDTO collaborateurDTO){
        Collaborateur collaborateur = new Collaborateur();
        BeanUtils.copyProperties(collaborateurDTO, collaborateur);
        return collaborateur;
    }

    public CommentaireDTO fromCommentairetoCommentaireDTO(Commentaire commentaire){
        CommentaireDTO commentaireDTO = new CommentaireDTO();
        BeanUtils.copyProperties(commentaire, commentaireDTO);
        return commentaireDTO;
    }
    public Commentaire fromCommentaireDTOtoCommentaire(CommentaireDTO commentaireDTO){
        Commentaire commentaire = new Commentaire();
        BeanUtils.copyProperties(commentaireDTO, commentaire);
        return commentaire;
    }

    public RequetteDTO fromRequettetoRequetteDTO(Requette requette){
        RequetteDTO requetteDTO = new RequetteDTO();
        BeanUtils.copyProperties(requette, requetteDTO);
        requetteDTO.setClientId(requette.getClient().getId());
        return requetteDTO;
    }
    public Requette fromRequetteDTOtoRequette(RequetteDTO requetteDTO){
        Requette requette = new Requette();
        BeanUtils.copyProperties(requetteDTO, requette);
        return requette;
    }

    public TacheDTO fromTachetoTacheDTO(Tache tache){
        TacheDTO tacheDTO = new TacheDTO();
        BeanUtils.copyProperties(tache, tacheDTO);
        //tacheDTO.setCollaborateurId(tache.getCollaborateur().getId());
        tacheDTO.setRequetteId(tache.getRequette().getId());
        return tacheDTO;
    }
    public TacheDTO fromTacheNonPlanifietoTacheDTO(Tache tache){
        TacheDTO tacheDTO = new TacheDTO();
        BeanUtils.copyProperties(tache, tacheDTO);
        //tacheDTO.setCollaborateurId(tache.getCollaborateur().getId());
        tacheDTO.setRequetteId(tache.getRequette().getId());
        return tacheDTO;
    }
    public Tache fromTacheDTOtoTacheNonPlanifie(TacheDTO tacheDTO){
        Tache tache = new Tache();
        BeanUtils.copyProperties(tacheDTO, tache);
        return tache;
    }
    public Tache fromTacheDTOtoTache(TacheDTO tacheDTO){
        Tache tache = new Tache();
        BeanUtils.copyProperties(tacheDTO, tache);
        return tache;
    }
}
