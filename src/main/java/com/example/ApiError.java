package com.example;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * DTO for handling exceptions.
 */

@Getter
@Setter
@NoArgsConstructor
public class ApiError implements Serializable {

    private HttpStatus status;

    private String message;

    public ApiError(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

}
