package projet.suivie_requetes.exceptions;

public class CollaborateurNotFoundException extends Exception {
    public CollaborateurNotFoundException (String message){
        super(message);
    }
    public CollaborateurNotFoundException (){
        super("CollaborateurNotFound");
    }
}
