package serv.controller;

import serv.api.KeyGeneratorApi;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KeyGeneratorController implements KeyGeneratorApi {

    @Override
    public String generate(){
        return generateKey();
    }

    private static String generateKey(){
        return Encoders.BASE64.encode(Keys.secretKeyFor(SignatureAlgorithm.HS512).getEncoded());
    }
}
