package serv.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import serv.server.dto.JwtRegistrRequestDto;
import serv.server.dto.JwtResponseDto;

@RequestMapping("/reg")
public interface RegApi {
    @PostMapping("/name")
    @ResponseStatus(HttpStatus.OK)
    JwtResponseDto proveName(@RequestBody JwtRegistrRequestDto provNameRequest);

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    JwtResponseDto proveEmail(@RequestBody JwtRegistrRequestDto proveEmailRequest);

    @PostMapping("/make")
    @ResponseStatus(HttpStatus.OK)
    JwtResponseDto register(@RequestBody JwtRegistrRequestDto request);
}
