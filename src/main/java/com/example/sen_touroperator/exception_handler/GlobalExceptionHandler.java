package com.example.sen_touroperator.exception_handler;

import com.example.sen_touroperator.exception_handler.exceptions.*;
import com.example.sen_touroperator.models.DAO.Landmark;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailPatternInvalid.class)
    public ResponseEntity<?> handleResourceNotFoundException
            (EmailPatternInvalid exception, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(new Date(),
                exception.getMessage(),
                request.getDescription(false),
                HttpStatus.UNPROCESSABLE_ENTITY);
        return new ResponseEntity(errorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<?> handleResourceNotFoundException
            (UserException exception, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(new Date(),
                exception.getMessage(),
                request.getDescription(false),
                HttpStatus.CONFLICT);
        return new ResponseEntity(errorDetails, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RewardException.class)
    public ResponseEntity<?> handleResourceNotFoundException
            (RewardException exception, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(new Date(),
                exception.getMessage(),
                request.getDescription(false),
                HttpStatus.NOT_FOUND);
        return new ResponseEntity(errorDetails, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(LandmarkException.class)
    public ResponseEntity<?> handleResourceNotFoundException
            (LandmarkException exception, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(new Date(),
                exception.getMessage(),
                request.getDescription(false),
                HttpStatus.NOT_FOUND);
        return new ResponseEntity(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RegionException.class)
    public ResponseEntity<?> handleResourceNotFoundException
            (RegionException exception, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(new Date(),
                exception.getMessage(),
                request.getDescription(false),
                HttpStatus.NOT_FOUND);
        return new ResponseEntity(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleResourceNotFoundException
            (AccessDeniedException exception, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(new Date(),
                exception.getMessage(),
                request.getDescription(false),
                HttpStatus.UNAUTHORIZED);
        return new ResponseEntity(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(PasswordPatternInvalid.class)
    public ResponseEntity<?> handleResourceNotFoundException
            (PasswordPatternInvalid exception, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(new Date(),
                exception.getMessage(),
                request.getDescription(false),
                HttpStatus.UNAUTHORIZED);
        return new ResponseEntity(errorDetails, HttpStatus.UNAUTHORIZED);
    }



}
