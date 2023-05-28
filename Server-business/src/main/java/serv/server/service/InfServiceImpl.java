package serv.server.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import serv.model.User;
import serv.server.api.InfService;
import serv.server.api.TokenProvider;
import serv.server.dto.JwtInfoUserRequestDto;
import serv.service.FindUser;

@Service
@RequiredArgsConstructor
public class InfServiceImpl implements InfService {
    final private TokenProvider tokenProvider;
    @Override
    public User getInformationAbout(@NonNull JwtInfoUserRequestDto infRequest) {
        final String login = infRequest.getLogin();
        if(tokenProvider.validateAccessToken(infRequest.getAccessToken())){
            return FindUser.findUserByMail(login);
        } else{
            return User.builder().username("token").build();
        }
    }
}
