package com.example.sen_touroperator.exception_handler.exceptions;

public class EmailPatternInvalid extends RuntimeException{
    public EmailPatternInvalid() {
        super("Email Invalid Pattern");
    }
}
