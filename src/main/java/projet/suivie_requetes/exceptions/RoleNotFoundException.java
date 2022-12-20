package projet.suivie_requetes.exceptions;

public class RoleNotFoundException extends Throwable {
    public RoleNotFoundException(String message) {
        super(message);
    }
    public RoleNotFoundException() {
        super("Role not found !!");
    }
}
