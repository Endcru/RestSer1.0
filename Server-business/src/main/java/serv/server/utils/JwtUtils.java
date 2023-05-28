package serv.server.utils;

import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import serv.server.domain.JwtAuthentication;
import serv.server.domain.Role;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtUtils {

    public static JwtAuthentication generate(Claims claims){
        final var jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setRoles(getRoles(claims));
        jwtInfoToken.setFirstName(claims.get("firstName", String.class));
        jwtInfoToken.setUsername(claims.getSubject());
        return jwtInfoToken;
    }

    private static Set<Role> getRoles(Claims claims){
        final String role = claims.get("role", String.class);
        Set<Role> roles = new HashSet<>();
        if(role.equals("customer")){
            roles.add(Role.CUSTOMER);
        } else if(role.equals("chef")){
            roles.add(Role.CHEF);
        }else if(role.equals("manager")){
            roles.add(Role.MANAGER);
        }
        return roles;
    }
}
