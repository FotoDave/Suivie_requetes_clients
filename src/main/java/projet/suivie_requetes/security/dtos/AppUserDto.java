package projet.suivie_requetes.security.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AppUserDto {
    private Long id;
    private String username;
    private String password;
    private Long clientId;
    private String nomClient;
    private ArrayList<String> roles;
}
