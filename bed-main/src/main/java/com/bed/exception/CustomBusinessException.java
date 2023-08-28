package com.bed.exception;

public class CustomBusinessException extends RuntimeException{

    public CustomBusinessException(String message){
        super(message);
    }
}
