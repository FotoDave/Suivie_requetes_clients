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
import projet.suivie_requetes.security.dtos.AppUserDto;
import projet.suivie_requetes.security.service.SecurityServiceImpl;

import java.util.ArrayList;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SuivieRequetesApplication {

    public static void main(String[] args) {

        SpringApplication.run(SuivieRequetesApplication.class, args);
    }

    /*@Bean
    CommandLineRunner commandLineRunner(SecurityServiceImpl service) {
        return args -> {
            ArrayList<String> roles = new ArrayList<String>();
            roles.add("Admin");
            AppUserDto userDto = new AppUserDto();
            userDto.setUsername("Admin");
            userDto.setPassword("1234");
            userDto.setRoles(roles);
            try {
                service.addNewUser(userDto);
            } catch (RoleNotFoundException e) {
                throw new RuntimeException(e);
            }
            service.listUsers();
        };
    }*/

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
