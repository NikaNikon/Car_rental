package com.litovchenko.carsapp.service;

public class UserInputException extends RuntimeException {
    public UserInputException (){super();}
    public UserInputException (String message){super(message);}
    public UserInputException (String message, Throwable cause){super(message, cause);}
    public UserInputException (Throwable cause){super(cause);}
}
