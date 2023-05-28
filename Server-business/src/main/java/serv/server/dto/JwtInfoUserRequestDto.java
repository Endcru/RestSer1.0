package serv.server.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtInfoUserRequestDto {
    private String login;
    private String accessToken;
}
