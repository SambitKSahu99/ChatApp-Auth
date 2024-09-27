package com.elixr.ChatApp_Auth.exceptionhandling;

public class UserNotFoundException extends Exception{

    public UserNotFoundException(String exceptionMessage){
        super(exceptionMessage);
    }
}
