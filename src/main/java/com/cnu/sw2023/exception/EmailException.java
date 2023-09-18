package com.cnu.sw2023.exception;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public class EmailException extends Exception {
    public EmailException(String message) {
        super(message);
    }
}