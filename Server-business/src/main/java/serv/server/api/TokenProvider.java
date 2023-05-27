package serv.server.api;

import io.jsonwebtoken.Claims;
import lombok.NonNull;
import serv.server.domain.User;

public interface TokenProvider {
    String generateAccessToken(@NonNull User user);
    String generateRefreshToken(@NonNull User user);
    boolean validateAccessToken(@NonNull String accessToken);
    boolean validateRefreshToken(@NonNull String refreshToken);
    Claims getAccessClaims(@NonNull String token);
    Claims getRefreshClaims(@NonNull String token);



}
