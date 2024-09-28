package com.imag.taxcalculator.exceptionhandler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.imag.taxcalculator.dto.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	
	
	 @ExceptionHandler(MethodArgumentNotValidException.class)
	    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
	        Map<String, String> errors = new HashMap<>();
	        ex.getBindingResult().getAllErrors().forEach((error) -> {
	            String fieldName = ((FieldError) error).getField();
	            String errorMessage = error.getDefaultMessage();
	            errors.put(fieldName, errorMessage);
	        });
	        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	    }

	    @ExceptionHandler(EmployeeNotFoundException.class)
	    public ResponseEntity<ErrorResponse> handleEmployeeNotFound(EmployeeNotFoundException ex) {
	    	log.error(ex.getMessage());
	    	ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value());
	        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	    }
	    
	    
	    @ExceptionHandler(EmployeIdAlredyexitException.class)
	    public ResponseEntity<ErrorResponse> handleEmployeeIdExists(EmployeIdAlredyexitException ex) {
	    	log.error(ex.getMessage());
	    	ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.CONFLICT.value());
	        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	    }
	    
	    
	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
	    	log.info(ex.getMessage());
	    	ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.EXPECTATION_FAILED.value());
	        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	    
	    
	    

}
