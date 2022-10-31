package projet.suivie_requetes.exceptions;

public class StatusNotFoundException extends Throwable {
    public StatusNotFoundException(String message){
        super(message);
    }
    public StatusNotFoundException(){
        super("Status tache not found");
    }
}
