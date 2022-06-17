package com.example.sen_touroperator.exception_handler;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String path;
    private HttpStatus httpStatus;

    public ErrorDetails(Date timestamp, String message, String path, HttpStatus httpStatus) {
        this.timestamp = timestamp;
        this.message = message;
        this.path = path;
        this.httpStatus = httpStatus;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public ErrorDetails setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ErrorDetails setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getPath() {
        return path;
    }

    public ErrorDetails setPath(String path) {
        this.path = path;
        return this;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ErrorDetails setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        return this;
    }
}
