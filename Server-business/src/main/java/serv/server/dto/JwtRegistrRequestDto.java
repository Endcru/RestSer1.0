package serv.server.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtRegistrRequestDto {
        private String username;
        private String login;
        private String password;
        private String role;
}
