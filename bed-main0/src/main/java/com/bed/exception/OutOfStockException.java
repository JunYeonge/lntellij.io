package com.bed.exception;

public class OutOfStockException extends RuntimeException{
    public OutOfStockException(String message){
        super(message);
    }
}
