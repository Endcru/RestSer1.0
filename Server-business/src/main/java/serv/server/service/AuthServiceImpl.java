package serv.server.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import serv.server.api.AuthService;
import serv.server.api.TokenProvider;
import serv.server.domain.JwtAuthentication;
import serv.server.dto.JwtRequestDto;
import serv.server.dto.JwtResponseDto;
import serv.service.AddSession;
import serv.service.FindUser;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    final private TokenProvider tokenProvider;
    private final Map<String, String> refreshStorage = new HashMap<>();
    @Override
    public JwtResponseDto login(@NonNull JwtRequestDto authRequest) {
        final var user = FindUser.findUserByMail(authRequest.getLogin());
        if(user == null){
            return new JwtResponseDto(null, null, "login");
        }
        if(user.getPasswordHash().equals(String.valueOf(authRequest.getPassword().hashCode()))){
            final String accessToken = tokenProvider.generateAccessToken(user);
            final String refreshToken = tokenProvider.generateRefreshToken(user);
            refreshStorage.put(user.getEmail(), refreshToken);
            AddSession.addUserSession(user.getId(), accessToken, tokenProvider.dateAccessToken());
            return new JwtResponseDto(accessToken, refreshToken, null);
        } else{
            return new JwtResponseDto(null, null, "password");
        }
    }

    @Override
    public JwtResponseDto getAccessToken(@NonNull String refreshToken) {
        if(tokenProvider.validateRefreshToken(refreshToken)){
            final var claims = tokenProvider.getRefreshClaims(refreshToken);
            final var login = claims.getSubject();
            final var saveRefreshToken = refreshStorage.get(login);
            if(saveRefreshToken != null && saveRefreshToken.equals(refreshToken)){
                final var user = FindUser.findUserByMail(login);
                final var accessToken = tokenProvider.generateAccessToken(user);
                return new JwtResponseDto(accessToken, null, null);
            }
        }
        return new JwtResponseDto(null, null, "token");
    }
    @Override
    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }
}
