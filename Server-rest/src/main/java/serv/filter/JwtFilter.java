package serv.filter;

import jakarta.annotation.Nullable;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import serv.server.api.TokenProvider;
import serv.server.utils.JwtUtils;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {
    private static final String BEARER = "Bearer";
    private static final String AUTHORIZATION = "Authorization";

    private final TokenProvider tokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filter) throws IOException, ServletException {
       final var token = getTokenFromRequest((HttpServletRequest) request);
       if(token != null && tokenProvider.validateAccessToken(token)){
           final var claims = tokenProvider.getAccessClaims(token);
           final var jwtInfoToken = JwtUtils.generate(claims);
           jwtInfoToken.setAuthenticated(true);
           SecurityContextHolder.getContext().setAuthentication(jwtInfoToken);
       }
       filter.doFilter(request, response);
    }

    @Nullable
    private String getTokenFromRequest(HttpServletRequest request){
        final var bearer = request.getHeader(AUTHORIZATION);
        if(StringUtils.hasText(bearer) && bearer.startsWith(BEARER)){
            return bearer.substring(BEARER.length());
        }
        return null;
    }

}
