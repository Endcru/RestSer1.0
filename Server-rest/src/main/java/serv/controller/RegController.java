package serv.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import serv.api.RegApi;
import serv.server.api.RegService;
import serv.server.dto.JwtRegistrRequestDto;
import serv.server.dto.JwtResponseDto;

@RestController
@RequiredArgsConstructor
public class RegController implements RegApi {
    private final RegService regService;
    @Override
    public JwtResponseDto proveName(JwtRegistrRequestDto provNameRequest) {
        return regService.proveName(provNameRequest);
    }

    @Override
    public JwtResponseDto proveEmail(JwtRegistrRequestDto proveEmailRequest) {
        return regService.proveEmail(proveEmailRequest);
    }

    @Override
    public JwtResponseDto register(JwtRegistrRequestDto request) {
        return regService.register(request);
    }
}
