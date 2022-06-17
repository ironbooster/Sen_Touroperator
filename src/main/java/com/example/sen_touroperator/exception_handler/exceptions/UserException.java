package com.example.sen_touroperator.exception_handler.exceptions;

import java.util.function.Supplier;

public class UserException extends RuntimeException{

    public UserException(String message) {
        super(message);
    }

}
