package projet.suivie_requetes.exceptions;

public class ClientNotFoundException extends Exception {
    public ClientNotFoundException(String message) {
        super(message);
    }
    public ClientNotFoundException() {
        super("ClientNotFound");
    }
}
