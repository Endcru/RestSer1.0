package serv.api;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import serv.server.dto.JwtRequestDto;
import serv.server.dto.JwtResponseDto;
import serv.server.dto.RefreshJwtRequestDto;

@RequestMapping("/auth")
public interface AuthApi {
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    JwtResponseDto login(@RequestBody JwtRequestDto authRequest);

    @PostMapping("/token")
    @ResponseStatus(HttpStatus.OK)
    JwtResponseDto getNewAccessToken(@RequestBody RefreshJwtRequestDto request);

    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.OK)
    JwtResponseDto getNewRefreshToken(@RequestBody RefreshJwtRequestDto request);
}