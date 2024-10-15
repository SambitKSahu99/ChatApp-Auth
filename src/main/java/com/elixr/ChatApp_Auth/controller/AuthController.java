package com.elixr.ChatApp_Auth.controller;

import com.elixr.ChatApp_Auth.contants.AuthConstants;
import com.elixr.ChatApp_Auth.contants.LoggerInfoConstants;
import com.elixr.ChatApp_Auth.dto.AuthUserDto;
import com.elixr.ChatApp_Auth.dto.LoginResponseDto;
import com.elixr.ChatApp_Auth.exceptionhandling.UserException;
import com.elixr.ChatApp_Auth.response.Response;
import com.elixr.ChatApp_Auth.service.AuthService;
import com.elixr.ChatApp_Auth.service.LogoutHandlerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin(origins = AuthConstants.ALLOWED_HEADERS)
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final LogoutHandlerService logoutHandlerService;

    public AuthController(AuthService authService, LogoutHandlerService logoutHandlerService) {
        this.authService = authService;
        this.logoutHandlerService = logoutHandlerService;
    }

    @PostMapping(AuthConstants.LOGIN_API_ENDPOINT)
    public ResponseEntity<Response> login(@RequestBody AuthUserDto authUserDto) throws UserException {
        LoginResponseDto responseDto = authService.login(authUserDto);
        MDC.put("userName", authUserDto.getUserName());
        log.info(LoggerInfoConstants.USER_LOGIN, authUserDto.getUserName());
        return new ResponseEntity<>(new Response(responseDto), HttpStatus.OK);
    }

    @PostMapping(AuthConstants.LOGOUT_URL)
    public void logout(@RequestBody String currentUser, HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        logoutHandlerService.onLogoutSuccess(request, response, authentication);
    }
}
