package com.elixr.ChatApp_Auth.filter;

import com.elixr.ChatApp_Auth.contants.MessagesConstants;
import com.elixr.ChatApp_Auth.response.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Response customErrorResponse = new Response();
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        customErrorResponse.setResponse(MessagesConstants.NOT_VALID_TOKEN);
        response.getWriter().write(objectMapper.writeValueAsString(customErrorResponse));
    }
}
