package serv.server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import serv.server.api.RegService;
import serv.server.api.TokenProvider;
import serv.server.dto.JwtRegistrRequestDto;
import serv.server.dto.JwtResponseDto;
import serv.service.AddUser;
import serv.service.FindUser;

@Service
@RequiredArgsConstructor
public class RegServiceImpl implements RegService {
    @Override
    public JwtResponseDto proveName(JwtRegistrRequestDto provNameRequest) {
        String name = provNameRequest.getUsername();
        final var user = FindUser.findUserByUsername(name);
        if(user == null){
            return new JwtResponseDto(null, null, null);
        }
        return new JwtResponseDto(null, null, "name");
    }

    @Override
    public JwtResponseDto proveEmail(JwtRegistrRequestDto proveEmailRequest) {
        final var user = FindUser.findUserByMail(proveEmailRequest.getLogin());
        if(user == null){
            return new JwtResponseDto(null, null, null);
        }
        return new JwtResponseDto(null, null, "email");
    }

    @Override
    public JwtResponseDto register(JwtRegistrRequestDto request) {
        AddUser.addUser(request.getUsername(), request.getLogin(), request.getPassword(), request.getRole());
        return new JwtResponseDto(null, null, null);
    }
}
