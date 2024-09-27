package com.elixr.ChatApp_Auth.exceptionhandling;

import com.elixr.ChatApp_Auth.contants.MessagesConstants;
import com.elixr.ChatApp_Auth.response.Response;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<Response> handleUserException(UserException userException){
        List<String> errors = new ArrayList<>();
        errors.add(userException.getMessage());
        return new ResponseEntity<>(new Response(errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Response> handleUserNotFoundException(UserNotFoundException userNotFoundException){
        List<String> errors = new ArrayList<>();
        errors.add(userNotFoundException.getMessage());
        return new ResponseEntity<>(new Response(errors),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Response> handleBadCredentials(BadCredentialsException badCredentialsException){
        List<String> errors = new ArrayList<>();
        errors.add(badCredentialsException.getMessage());
        return new ResponseEntity<>(new Response(errors),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<String> handleJwtException(JwtException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(MessagesConstants.NOT_VALID_TOKEN + ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleException(Exception exception){
        List<String> errors = new ArrayList<>();
        errors.add(exception.getMessage());
        return new ResponseEntity<>(new Response(errors),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
