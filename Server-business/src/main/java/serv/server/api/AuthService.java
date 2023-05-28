package serv.server.api;

import lombok.NonNull;
import serv.server.domain.JwtAuthentication;
import serv.server.dto.JwtRequestDto;
import serv.server.dto.JwtResponseDto;

public interface AuthService {
    JwtResponseDto login(@NonNull JwtRequestDto authRequest);
    JwtResponseDto getAccessToken(@NonNull String refreshToken);
    JwtAuthentication getAuthInfo();
}
