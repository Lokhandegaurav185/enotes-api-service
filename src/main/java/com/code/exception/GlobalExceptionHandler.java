package com.code.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler{
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<?> handleNullPointException(Exception e){
		return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotException(Exception e){
		return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(DtoValidationException.class)
	public ResponseEntity<?> handleDtoValidationException(DtoValidationException e){
		return new ResponseEntity<>(e.getErrors(),HttpStatus.BAD_REQUEST);
	}
}
