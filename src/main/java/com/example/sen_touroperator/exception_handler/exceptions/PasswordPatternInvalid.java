package com.example.sen_touroperator.exception_handler.exceptions;

public class PasswordPatternInvalid extends RuntimeException {
    public PasswordPatternInvalid() {
        super("Тhe password must contain at least one letter and at least one number and be 8 to 20 characters long");
    }
}
