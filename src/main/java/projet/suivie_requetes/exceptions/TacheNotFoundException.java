package projet.suivie_requetes.exceptions;

public class TacheNotFoundException extends Exception {
    public TacheNotFoundException(String message){
        super (message);
    }
    public TacheNotFoundException(){
        super ("TacheNotFound");
    }
}
