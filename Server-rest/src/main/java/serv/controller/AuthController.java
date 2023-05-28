package serv.controller;

import serv.api.AuthApi;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import serv.server.api.AuthService;
import serv.server.dto.JwtRequestDto;
import serv.server.dto.JwtResponseDto;
import serv.server.dto.RefreshJwtRequestDto;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {
    private final AuthService authService;
    @Override
    public JwtResponseDto login(@RequestBody JwtRequestDto authRequest) {
        return authService.login(authRequest);
    }

    @Override
    public JwtResponseDto getNewAccessToken(@RequestBody RefreshJwtRequestDto request) {
        return authService.getAccessToken(request.getRefreshToken());
    }
}