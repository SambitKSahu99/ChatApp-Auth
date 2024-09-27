package com.elixr.ChatApp_Auth.controller;

import com.elixr.ChatApp_Auth.contants.AuthConstants;
import com.elixr.ChatApp_Auth.contants.UrlConstants;
import com.elixr.ChatApp_Auth.dto.AuthUserDto;
import com.elixr.ChatApp_Auth.dto.LoginResponseDto;
import com.elixr.ChatApp_Auth.exceptionhandling.UserException;
import com.elixr.ChatApp_Auth.response.Response;
import com.elixr.ChatApp_Auth.service.AuthService;
import com.elixr.ChatApp_Auth.service.JwtService;
import com.elixr.ChatApp_Auth.service.UserDetailServiceImplementation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = AuthConstants.ALLOWED_HEADERS)
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;
    private final UserDetailServiceImplementation userDetailServiceImplementation;

    public AuthController(AuthService authService, JwtService jwtService, UserDetailServiceImplementation userDetailServiceImplementation) {
        this.authService = authService;
        this.jwtService = jwtService;
        this.userDetailServiceImplementation = userDetailServiceImplementation;
    }

    @PostMapping(UrlConstants.LOGIN_API_ENDPOINT)
    public ResponseEntity<Response> login(@RequestBody AuthUserDto authUserDto) throws UserException {
        String currentUser = authUserDto.getUserName();
        LoginResponseDto responseDto = authService.login(authUserDto);
        responseDto.setUserName(currentUser);
        return new ResponseEntity<>(new Response(responseDto), HttpStatus.OK);
    }

    @PostMapping(UrlConstants.VERIFY_TOKEN_ENDPOINT)
    public ResponseEntity<String> verifyToken(@RequestHeader(AuthConstants.AUTHORIZATION_HEADER_TYPE) String token) throws UserException {
        String jwtToken = token.substring(7);
        String userName = jwtService.extractUserName(jwtToken);
        UserDetails userDetails = userDetailServiceImplementation.loadUserByUsername(userName);
        boolean isValid = jwtService.validateToken(jwtToken, userDetails);
        return new ResponseEntity<>(userName, HttpStatus.OK);
    }
}
