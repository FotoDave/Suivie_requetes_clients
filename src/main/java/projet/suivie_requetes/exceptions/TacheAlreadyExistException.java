package projet.suivie_requetes.exceptions;

public class TacheAlreadyExistException extends Exception {
    public TacheAlreadyExistException(String message) {
        super(message);
    }
    public TacheAlreadyExistException() {
        super("Le client existe deja");
    }
}
