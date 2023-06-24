package com.pdfmanager.exception;

import com.pdfmanager.dtos.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidParameterException(InvalidParameterException ex) {

        ErrorResponseDto errorResponse = new ErrorResponseDto();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<ErrorResponseDto> handleInternalServerException(InvalidParameterException ex) {


        ErrorResponseDto errorResponse = new ErrorResponseDto();
        errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);

    }



}
