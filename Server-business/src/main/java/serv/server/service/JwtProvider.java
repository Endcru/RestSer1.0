package serv.server.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import serv.model.User;
import serv.server.api.TokenProvider;

import javax.crypto.SecretKey;
import java.security.Key;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
@Component
public class JwtProvider implements TokenProvider {
    private static  final long ACCESS_EXPIRATION_MINUTES = 5;
    private static final long REFRESH_EXPIRATION_DAYS = 1;

    private final SecretKey jwtAccessSecret;
    private final SecretKey jwtRefreshSecret;

    public JwtProvider(@Value("${jwt.secret.access}") String jwtAccessSecret, @Value("${jwt.secret.refresh}") String jwtRefreshSecret){
        this.jwtAccessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret));
        this.jwtRefreshSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtRefreshSecret));
    }
    @Override
    public String generateAccessToken(@NonNull User user) {
        final var now = LocalDateTime.now();
        final var accessExpirationInstant = now.plusMinutes(ACCESS_EXPIRATION_MINUTES).atZone(ZoneId.systemDefault()).toInstant();
        final var accessExpiration = Date.from(accessExpirationInstant);
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setExpiration(accessExpiration)
                .signWith(jwtAccessSecret)
                .claim("roles", user.getRole())
                .claim("username", user.getUsername())
                .compact();
    }

    @Override
    public java.util.Date dateAccessToken() {
        final var now = LocalDateTime.now();
        final var accessExpirationInstant = now.plusMinutes(ACCESS_EXPIRATION_MINUTES).atZone(ZoneId.systemDefault()).toInstant();
        final var accessExpiration = Date.from(accessExpirationInstant);
        return accessExpiration;
    }

    @Override
    public String generateRefreshToken(@NonNull User user) {
        final var now = LocalDateTime.now();
        final var refreshExpirationInstant = now.plusDays(REFRESH_EXPIRATION_DAYS).atZone(ZoneId.systemDefault()).toInstant();
        final var refreshExpiration = Date.from(refreshExpirationInstant);
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setExpiration(refreshExpiration)
                .signWith(jwtRefreshSecret)
                .compact();
    }

    @Override
    public boolean validateAccessToken(@NonNull String accessToken) {
        return validateToken(accessToken, jwtAccessSecret);
    }

    @Override
    public boolean validateRefreshToken(@NonNull String refreshToken) {
        return validateToken(refreshToken, jwtRefreshSecret);
    }

    @Override
    public Claims getAccessClaims(@NonNull String token) {
        return getClaims(token, jwtAccessSecret);
    }

    @Override
    public Claims getRefreshClaims(@NonNull String token) {
        return getClaims(token, jwtRefreshSecret);
    }
    private boolean validateToken(@NonNull String token, @NonNull Key secret){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expiredJwtException){
            log.error("Token has expired", expiredJwtException);
        } catch (UnsupportedJwtException unsupportedJwtException){
            log.error("Unsupported jwt", unsupportedJwtException);
        } catch (MalformedJwtException malformedJwtException){
            log.error("Malformed jwt", malformedJwtException);
        } catch (SignatureException invalidSignature){
            log.error("Invalid signature", invalidSignature);
        } catch (Exception e){
            log.error("invalid token", e);
        }
        return false;
    }
    private Claims getClaims(@NonNull String token, @NonNull Key secret){
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
