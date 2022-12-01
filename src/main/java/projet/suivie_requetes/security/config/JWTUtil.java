package projet.suivie_requetes.security.config;

public class JWTUtil {
    public static final String SECRET = "Foto237*";
    public static final String AUTH_HEADER = "Authorization";
    public static final String PREFIX_TOKEN = "Bearer ";
    public static final long EXPIRED_ACCESS_TOKEN = 1*60*1000;
    public static final long EXPIRED_REFRESH_TOKEN = 15*60*1000;
}
