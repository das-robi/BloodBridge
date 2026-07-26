package com.robindas.bloodbridge.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponses {

    private LocalDateTime localDateTime;
    private int status;
    private String error;
    private String message;
    private String path;

}
