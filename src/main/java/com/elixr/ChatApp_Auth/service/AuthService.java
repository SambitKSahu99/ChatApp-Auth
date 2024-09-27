package com.elixr.ChatApp_Auth.service;

import com.elixr.ChatApp_Auth.contants.MessagesConstants;
import com.elixr.ChatApp_Auth.dto.AuthUserDto;
import com.elixr.ChatApp_Auth.exceptionhandling.UserException;
import com.elixr.ChatApp_Auth.filter.JwtFilter;
import com.elixr.ChatApp_Auth.dto.LoginResponseDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final JwtFilter jwtFilter;

    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService, JwtFilter jwtFilter) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.jwtFilter = jwtFilter;
    }

    public LoginResponseDto login(AuthUserDto authUserDto) throws UserException {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        authUserDto.getUserName(),authUserDto.getPassword()
                );
        Authentication authentication =
                authenticationManager.authenticate(authenticationToken);
        if(!authentication.isAuthenticated()){
            throw new UserException(MessagesConstants.BAD_CREDENTIALS);
        }
        String token = jwtService.generateToken(authUserDto.getUserName());
        return LoginResponseDto.builder()
                .token(token)
                .build();
    }
}
