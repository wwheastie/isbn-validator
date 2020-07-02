package com.acme.isbnvalidator.exception;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String status;

    public ErrorDetails(String message, int status) {
        this.timestamp = new Date();
        this.message = message;
        this.status = String.valueOf(status);
    }
}
