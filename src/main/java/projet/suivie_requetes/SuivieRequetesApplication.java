package projet.suivie_requetes;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import projet.suivie_requetes.exceptions.RoleNotFoundException;
import projet.suivie_requetes.exceptions.UserNotFoundException;
import projet.suivie_requetes.security.dtos.AppUserDto;
import projet.suivie_requetes.security.entities.AppRole;
import projet.suivie_requetes.security.repository.AppUserRepository;
import projet.suivie_requetes.security.service.SecurityServiceImpl;

import java.util.ArrayList;
import java.util.Optional;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SuivieRequetesApplication {

    public static void main(String[] args) {

        SpringApplication.run(SuivieRequetesApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(SecurityServiceImpl service, AppUserRepository appUserRepository) {
        return args -> {
            AppRole appRole1 = new AppRole(null,"Admin");
            AppRole appRole2 = new AppRole(null,"Collaborateur");
            AppRole appRole3 = new AppRole(null,"Client");
            service.addNewRole(appRole1);
            service.addNewRole(appRole2);
            service.addNewRole(appRole3);
            ArrayList<String> roles = new ArrayList<String>();
            roles.add("Admin");
            AppUserDto userDto = new AppUserDto();
            userDto.setUsername("SuperUser");
            userDto.setPassword("Hello1234");
            userDto.setRoles(roles);
            if(Optional.ofNullable(appUserRepository.findByUsername(userDto.getUsername())).isEmpty()){
                try {
                    AppUserDto appUserDto = service.addNewUser(userDto);
                    System.out.println("*************************");
                    System.out.println("Informations de l'utilisateur : ");
                    System.out.println(appUserDto.getUsername());
                    System.out.println(appUserDto.getRoles());
                } catch (RoleNotFoundException | UserNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer() {
            //Permet de palier au problème du CORS Policy
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedOrigins("*")
                        .allowedHeaders("*");;
            }
        };
    }

}
