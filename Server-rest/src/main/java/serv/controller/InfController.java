package serv.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import serv.api.InformApi;
import serv.model.User;
import serv.server.api.AuthService;
import serv.server.api.InfService;
import serv.server.dto.JwtInfoUserRequestDto;
import serv.server.dto.JwtMenuDto;
import serv.server.dto.JwtRequestDto;

@RestController
@RequiredArgsConstructor
public class InfController implements InformApi {
    private final InfService infService;
    @Override
    public User findUser(JwtInfoUserRequestDto infRequest) {
        return infService.getInformationAbout(infRequest);
    }

    @Override
    public JwtMenuDto getMenu() {
        return infService.getMenu();
    }
}
