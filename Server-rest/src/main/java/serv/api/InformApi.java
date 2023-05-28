package serv.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import serv.model.User;
import serv.server.dto.JwtInfoUserRequestDto;


@RequestMapping("/info")
public interface InformApi {
    @PostMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    User findUser(@RequestBody JwtInfoUserRequestDto infRequest);
}
