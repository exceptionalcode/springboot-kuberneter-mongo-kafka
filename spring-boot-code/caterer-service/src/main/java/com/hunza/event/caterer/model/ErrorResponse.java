package com.hunza.event.caterer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author ishaan.solanki
 * <p>
 * ErrorResponse class is responsible create an error response against any exception that occurs in the caterer-service system.
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ErrorResponse {

    private String timestamp;
    private int status;
    private String error;
    private String path;
    private String cause;
}
