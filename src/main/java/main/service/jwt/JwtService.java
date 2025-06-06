package main.service.jwt;

public interface JwtService {

    String generateJwt(String username, String perfil);
    
}