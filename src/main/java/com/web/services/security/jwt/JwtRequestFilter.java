package com.web.services.security.jwt;

import com.web.services.Setting;
import com.web.services.rest.utility.http.status.Http400BadRequest;
import com.web.services.rest.utility.http.status.Http401Unauthorized;
import com.web.services.rest.utility.http.status.Http406NotAccepted;
import com.web.services.rest.utility.http.status.Http500InternalServerError;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private FilterResponse filterResponse;
    private UserDetailsService userService;
    private JwtUtilityTool jwtUtility;

    public JwtRequestFilter(FilterResponse filterResponse, UserDetailsService userService, JwtUtilityTool jwtUtility) {
        this.filterResponse = filterResponse;
        this.userService = userService;
        this.jwtUtility = jwtUtility;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        if (request.getRequestURI().equals("") || request.getRequestURI().equals("/")) {
            response.sendRedirect("swagger-ui.html");
            return;
        }

        try {
            String authHeader = request.getHeader("Authorization");
            String username;
            String jwt;
            UserDetails userDetails;

            if ((authHeader != null) && !authHeader.startsWith("Bearer ")) { throw new Http406NotAccepted(); }
            else if ((authHeader != null) && authHeader.startsWith("Bearer ")) {
                jwt = authHeader.substring(7);
                username = jwtUtility.getUsername(jwt);

                if ((username != null) && (SecurityContextHolder.getContext().getAuthentication() == null)) {
                    userDetails = userService.loadUserByUsername(username);

                    if (!userDetails.isAccountNonExpired()) {
                        throw new Http401Unauthorized(Setting.AUTH_ACCOUNT_BAN_MESSAGE);
                    }
                    else if (!userDetails.isAccountNonLocked()) {
                        throw new Http401Unauthorized(Setting.AUTH_LOCKED_MESSAGE);
                    }

                    if (jwtUtility.validateToken(jwt, userDetails)) {
                        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());

                        token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(token);
                    }
                }
            }
            chain.doFilter(request, response);
            return;
        }
        catch (MalformedJwtException exception) {
            filterResponse.setHttpException(new Http400BadRequest(Setting.JWT_MALFORMED));
        }
        catch (ExpiredJwtException exception) {
            filterResponse.setHttpException(new Http401Unauthorized(Setting.JWT_EXPIRED));
        }
        catch (Http401Unauthorized exception) {
            filterResponse.setHttpException(new Http401Unauthorized(exception.getMessage()));
        }
        catch (Http406NotAccepted exception) {
            filterResponse.setHttpException(new Http406NotAccepted(Setting.JWT_INVALID_HEADER));
        }
        catch (RuntimeException exception) {
            filterResponse.setHttpException(new Http500InternalServerError());
        }
        response.setContentType("application/json");
        response.setStatus(filterResponse.getHttpStatus().value());
        response.getWriter().write(filterResponse.getMessage());
    }
}
