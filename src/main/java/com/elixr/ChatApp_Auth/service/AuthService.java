package com.elixr.ChatApp_Auth.service;

import com.elixr.ChatApp_Auth.contants.LoggerInfoConstants;
import com.elixr.ChatApp_Auth.contants.MessagesConstants;
import com.elixr.ChatApp_Auth.dto.AuthUserDto;
import com.elixr.ChatApp_Auth.exceptionhandling.UserException;
import com.elixr.ChatApp_Auth.dto.LoginResponseDto;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    @Getter
    private String currentUser;

    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public LoginResponseDto login(AuthUserDto authUserDto) throws UserException {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        authUserDto.getUserName(),authUserDto.getPassword()
                );
        try {
            Authentication authentication =
                    authenticationManager.authenticate(authenticationToken);
        }catch(RuntimeException exception){
            log.warn(LoggerInfoConstants.USER_UNAUTHORISED,authUserDto.getUserName());
            throw new UserException(MessagesConstants.BAD_CREDENTIALS);
        }
        String token = jwtService.generateToken(authUserDto.getUserName());
        currentUser = authUserDto.getUserName();
        return LoginResponseDto.builder()
                .token(token)
                .build();
    }
}
