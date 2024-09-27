package com.elixr.ChatApp_Auth.filter;

import com.elixr.ChatApp_Auth.contants.AuthConstants;
import com.elixr.ChatApp_Auth.contants.MessagesConstants;
import com.elixr.ChatApp_Auth.service.JwtService;
import com.elixr.ChatApp_Auth.service.UserDetailServiceImplementation;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final org.springframework.context.ApplicationContext context;

    public JwtFilter(JwtService jwtService, ApplicationContext context) {
        this.jwtService = jwtService;
        this.context = context;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(AuthConstants.AUTHORIZATION_HEADER_TYPE);
        String token = null;
        String userName = null;
        try {
            if (authHeader != null && authHeader.startsWith(AuthConstants.BEARER)) {
                token = authHeader.substring(7);
                userName = jwtService.extractUserName(token);
            }
            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = context.getBean(UserDetailServiceImplementation.class).loadUserByUsername(userName);
                if (jwtService.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        filterChain.doFilter(request,response);
        }catch(JwtException exception){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, MessagesConstants.NOT_VALID_TOKEN);
        }catch (Exception exception){
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,MessagesConstants.SOME_ERROR_OCCURRED);
        }
    }
}
