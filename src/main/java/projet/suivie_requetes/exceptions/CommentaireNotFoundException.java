package projet.suivie_requetes.exceptions;

public class CommentaireNotFoundException extends Exception {
    public CommentaireNotFoundException(){
        super("CommentaireNotFound");
    }
    public CommentaireNotFoundException(String message){
        super(message);
    }
}
