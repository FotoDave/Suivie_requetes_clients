package projet.suivie_requetes.exceptions;

public class RequetteNotFoundException extends Exception {
    public RequetteNotFoundException(String message) {
        super(message);
    }
    public RequetteNotFoundException() {
        super("RequetteNotFound");
    }
}
