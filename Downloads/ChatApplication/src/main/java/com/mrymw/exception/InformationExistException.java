package com.mrymw.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class InformationExistException extends RuntimeException{
    public InformationExistException(String message) {
        super(message);
    }

}
