package com.pengyu.magnet.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Custom Response Data for Exceptions
 */
@Data
@AllArgsConstructor
public class ApiExceptionResponse {

    private final String path;
    private final String message;
    private final int status;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime localDateTime;


}
