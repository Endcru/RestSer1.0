package serv.server.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public enum Role implements GrantedAuthority {
    CUSTOMER("customer"),
    CHEF("chef"),
    MANAGER("manager");
    private final String name;
    @Override
    public String getAuthority() {
        return name;
    }
}
