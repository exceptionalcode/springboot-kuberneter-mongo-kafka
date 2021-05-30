package com.hunza.event.caterer.exception;


import com.hunza.event.caterer.model.ErrorResponse;
import com.hunza.event.caterer.utils.DateTimeUtility;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;

/**
 * @author ishaan.solanki
 *
 * Exception Handler for caterer REST services.
 *
 * <p>It is responsible for capturing Exception of type {@link Exception} , {@link ConstraintViolationException} and {@link MethodArgumentNotValidException}
 * and bind it with {@link ErrorResponse} whenever this occurs in the caterer service app</p>
 */
@ControllerAdvice
public class CatererAppExceptionHandler {

    /**
     * This will handle exception of type {@link Exception}
     *
     * <p>It is a handler which invokes when exception occurs.
     * It captures the exception details and bind it with {@link ErrorResponse} object</p>
     *
     * @param ex      {@link Exception}
     * @param request {@link WebRequest}
     * @return {@link ResponseEntity} of type {@link Object}
     */
    @ExceptionHandler(value = {Exception.class})
    public final ResponseEntity<Object> handleException(Exception ex, WebRequest request) {

        ErrorResponse errorResponseInternalServer = new ErrorResponse(DateTimeUtility.dateTimeForGlobalException(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), request.getDescription(false).replaceAll("uri=", ""),
                ex.getMessage());
        return new ResponseEntity(errorResponseInternalServer, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * This will handle exception of type {@link MethodArgumentNotValidException}
     *
     * <p>It is a handler which invokes when exception of type {@link MethodArgumentNotValidException} occurs.
     * It captures the exception details and bind it with {@link ErrorResponse} object</p>
     *
     * @param ex      {@link MethodArgumentNotValidException}
     * @param request {@link WebRequest}
     * @return {@link ResponseEntity} of type {@link Object}
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    protected ResponseEntity<Error> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                          WebRequest request) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ErrorResponse errorResponse = new ErrorResponse(DateTimeUtility.dateTimeForGlobalException(), badRequest.value(), badRequest.getReasonPhrase(),
                request.getDescription(false).replaceAll("uri=", ""),
                "Invalid " + ex.getBindingResult().getFieldErrors().stream().map(err -> err.getField())
                        .collect(java.util.stream.Collectors.joining(", ")));
        return new ResponseEntity(errorResponse, badRequest);
    }


    /**
     * This will handle exception of type {@link ConstraintViolationException}
     *
     * <p>It is a handler which invokes when exception of type {@link ConstraintViolationException} occurs.
     * It captures the exception details and bind it with {@link ErrorResponse} object</p>
     *
     * @param ex      {@link ConstraintViolationException}
     * @param request {@link WebRequest}
     * @return {@link ResponseEntity} of type {@link Object}
     */
    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<Error> handleConstraintViolationException(ConstraintViolationException ex,
                                                                       WebRequest request) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ErrorResponse errorResponse = new ErrorResponse(DateTimeUtility.dateTimeForGlobalException(), badRequest.value(),
                badRequest.getReasonPhrase(), request.getDescription(false).replaceAll("uri=", ""),
                ex.getConstraintViolations().stream().map(err -> err.getMessage())
                        .collect(java.util.stream.Collectors.joining(", ")));
        return new ResponseEntity(errorResponse, badRequest);
    }
}
