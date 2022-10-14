package projet.suivie_requetes.dtos;

import com.sun.istack.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ClientDTO {
    private Long id;
    private String nom;
    private String email;
    private String tel;
}
