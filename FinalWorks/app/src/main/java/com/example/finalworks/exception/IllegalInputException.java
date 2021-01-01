package com.example.finalworks.exception;

public class IllegalInputException extends Exception {
    public IllegalInputException(){
        super();
    }

    public IllegalInputException(String message){
        super(message);
    }

    public IllegalInputException(String message, Throwable cause){
        super(message, cause);
    }

    public IllegalInputException(Throwable casue){
        super(casue);
    }
}
