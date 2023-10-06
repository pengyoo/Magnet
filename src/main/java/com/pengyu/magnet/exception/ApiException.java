package com.pengyu.magnet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 404 Exception
 */
@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class ApiException extends RuntimeException {

    public ApiException(String message) {
        super(message);
    }
}
