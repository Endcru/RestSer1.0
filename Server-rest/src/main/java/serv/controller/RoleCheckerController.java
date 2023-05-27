package serv.controller;

import serv.api.RoleCheckerApi;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import serv.server.api.AuthService;

@RestController
@RequiredArgsConstructor
public class RoleCheckerController implements RoleCheckerApi {

    private final AuthService authService;

    @Override
    @PreAuthorize("hasAuthority('USER')")
    public String checkUser() {
        return "Hello, user " + authService.getAuthInfo().getPrincipal() + "!";
    }

    @Override
    @PreAuthorize("hasAuthority('Admin')")
    public String checkAdmin() {
        return "Hello, admin " + authService.getAuthInfo().getPrincipal() + "!";
    }
}
