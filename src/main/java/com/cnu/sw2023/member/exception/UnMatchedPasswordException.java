package com.cnu.sw2023.member.exception;

public class UnMatchedPasswordException extends RuntimeException{
    public UnMatchedPasswordException(String message){
        super(message);
    }
}
