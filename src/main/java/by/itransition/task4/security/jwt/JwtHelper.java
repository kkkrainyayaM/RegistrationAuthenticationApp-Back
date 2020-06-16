package by.itransition.task4.security.jwt;

import by.itransition.task4.entity.UserDetailsImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtHelper {

    private static final long JWT_EXPIRATION_MS = 86400000;

    @Value("${task4.jwtSecretKey}")
    private static String JWT_SECRET_KEY = "itransitionAuthorizationKey";

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String encodedSecret = Base64.getEncoder().encodeToString(JWT_SECRET_KEY.getBytes());
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(new Date().getTime() + JWT_EXPIRATION_MS))
                .signWith(SignatureAlgorithm.HS256, encodedSecret)
                .compact();
    }

    public String getUsernameFromJwtToken(String token) {
        String encodedSecret = Base64.getEncoder().encodeToString(JWT_SECRET_KEY.getBytes());
        return Jwts.parser().setSigningKey(encodedSecret)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            String encodedSecret = Base64.getEncoder().encodeToString(JWT_SECRET_KEY.getBytes());
            Jwts.parser().setSigningKey(encodedSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}
