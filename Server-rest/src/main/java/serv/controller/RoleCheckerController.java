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
    @PreAuthorize("hasAuthority('customer')")
    public String checkCustomer() {
        return "Hello, customer " + authService.getAuthInfo().getPrincipal() + "!";
    }

    @Override
    @PreAuthorize("hasAuthority('chef')")
    public String checkChef() {
        return "Hello, chef " + authService.getAuthInfo().getPrincipal() + "!";
    }

    @Override
    @PreAuthorize("hasAuthority('manager')")
    public String checkManager() {
        return "Hello, manager " + authService.getAuthInfo().getPrincipal() + "!";
    }
}
