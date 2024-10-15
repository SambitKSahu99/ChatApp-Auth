package com.elixr.ChatApp_Auth.service;

import com.elixr.ChatApp_Auth.contants.LoggerInfoConstants;
import com.elixr.ChatApp_Auth.contants.MessagesConstants;
import com.elixr.ChatApp_Auth.dto.AuthUserDto;
import com.elixr.ChatApp_Auth.exceptionhandling.UserException;
import com.elixr.ChatApp_Auth.dto.LoginResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public LoginResponseDto login(AuthUserDto authUserDto) throws UserException {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        authUserDto.getUserName(),authUserDto.getPassword()
                );
        String token;
        try {
            authenticationManager.authenticate(authenticationToken);
            token = jwtService.generateToken(authUserDto.getUserName());
        }catch(RuntimeException exception){
            log.warn(LoggerInfoConstants.USER_UNAUTHORISED,authUserDto.getUserName());
            throw new UserException(MessagesConstants.BAD_CREDENTIALS);
        }
        return LoginResponseDto.builder()
                .userName(authUserDto.getUserName())
                .token(token)
                .build();
    }
}
