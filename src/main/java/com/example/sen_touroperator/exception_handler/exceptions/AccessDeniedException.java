package com.example.sen_touroperator.exception_handler.exceptions;

public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException() {
        super("Client is not Admin");
    }
}
