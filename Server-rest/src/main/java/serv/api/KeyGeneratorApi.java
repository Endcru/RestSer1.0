package serv.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("keygen")
public interface KeyGeneratorApi {
    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/generate")
    String generate();
}
