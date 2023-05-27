package serv.server.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import serv.server.api.AuthService;
import serv.server.api.TokenProvider;
import serv.server.api.UserService;
import serv.server.domain.JwtAuthentication;
import serv.server.dto.JwtRequestDto;
import serv.server.dto.JwtResponseDto;
import serv.server.exception.AuthException;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    final private UserService userService;
    final private TokenProvider tokenProvider;
    private final Map<String, String> refreshStorage = new HashMap<>();
    @Override
    public JwtResponseDto login(@NonNull JwtRequestDto authRequest) {
        final var user = userService.getByLogin(authRequest.getLogin()).orElseThrow(AuthException::userNotFound);
        if(user.getPassword().equals(authRequest.getPassword())){
            final String accessToken = tokenProvider.generateAccessToken(user);
            final String refreshToken = tokenProvider.generateRefreshToken(user);
            refreshStorage.put(user.getLogin(), refreshToken);
            return new JwtResponseDto(accessToken, refreshToken);
        } else{
            throw AuthException.invalidPassword();
        }
    }

    @Override
    public JwtResponseDto getAccessToken(@NonNull String refreshToken) {
        if(tokenProvider.validateRefreshToken(refreshToken)){
            final var claims = tokenProvider.getRefreshClaims(refreshToken);
            final var login = claims.getSubject();
            final var saveRefreshToken = refreshStorage.get(login);
            if(saveRefreshToken != null & saveRefreshToken.equals(refreshToken)){
                final var user = userService.getByLogin(login).orElseThrow(AuthException::userNotFound);
                final var accessToken = tokenProvider.generateAccessToken(user);
                return new JwtResponseDto(accessToken, null);
            }
        }
        return new JwtResponseDto(null, null);
    }

    @Override
    public JwtResponseDto refresh(@NonNull String refreshToken) {
        if(tokenProvider.validateRefreshToken(refreshToken)){
            final var claims = tokenProvider.getRefreshClaims(refreshToken);
            final var login = claims.getSubject();
            final var saveRefreshToken = refreshStorage.get(login);
            if(saveRefreshToken != null & saveRefreshToken.equals(refreshToken)){
                final var user = userService.getByLogin(login).orElseThrow(AuthException::userNotFound);
                final var accessToken = tokenProvider.generateAccessToken(user);
                final var newRefreshToken = tokenProvider.generateRefreshToken(user);
                return new JwtResponseDto(accessToken, newRefreshToken);
            }
        }
        throw AuthException.invalidToken();
    }

    @Override
    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }
}
