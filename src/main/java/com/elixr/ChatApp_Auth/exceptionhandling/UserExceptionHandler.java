package com.elixr.ChatApp_Auth.exceptionhandling;

import com.elixr.ChatApp_Auth.contants.MessagesConstants;
import com.elixr.ChatApp_Auth.response.Response;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<Response> handleUserException(UserException userException){
        List<String> errors = new ArrayList<>();
        errors.add(userException.getMessage());
        log.error(userException.getMessage());
        return new ResponseEntity<>(new Response(errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Response> handleUserNotFoundException(UserNotFoundException userNotFoundException){
        List<String> errors = new ArrayList<>();
        errors.add(userNotFoundException.getMessage());
        log.error(userNotFoundException.getMessage());
        return new ResponseEntity<>(new Response(errors),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<String> handleJwtException(JwtException jwtException) {
        log.error(jwtException.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(MessagesConstants.NOT_VALID_TOKEN + jwtException.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleException(Exception exception){
        List<String> errors = new ArrayList<>();
        errors.add(exception.getMessage());
        log.error(exception.getMessage());
        return new ResponseEntity<>(new Response(errors),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
