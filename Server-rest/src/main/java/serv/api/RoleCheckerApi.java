package serv.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/check")
public interface RoleCheckerApi {
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/user")
    String checkUser();

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/admin")
    String checkAdmin();
}
