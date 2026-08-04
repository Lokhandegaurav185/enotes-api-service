package com.code.exception;

import java.io.FileNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.code.util.CommonGenericResponseUtil;

@ControllerAdvice
public class GlobalExceptionHandler{
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<?> handleNullPointException(Exception e){
		return CommonGenericResponseUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//		return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotException(Exception e){
		return CommonGenericResponseUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.NOT_FOUND);
//		return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<?> handleIllegalArgumentException(Exception e){
		return CommonGenericResponseUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.NOT_FOUND);
//		return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(DtoValidationException.class)
	public ResponseEntity<?> handleDtoValidationException(DtoValidationException e){
		return CommonGenericResponseUtil.createErrorResponse(e.getErrors(), HttpStatus.BAD_REQUEST);
//		return new ResponseEntity<>(e.getErrors(),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ExistDataException.class)
	public ResponseEntity<?> handleExistDataException(ExistDataException e){
		return CommonGenericResponseUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.CONFLICT);
//		return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<?> handleMessageNotReadableException(HttpMessageNotReadableException e){
		return CommonGenericResponseUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
//		return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(FileNotFoundException.class)
	public ResponseEntity<?> handleFileNotFoundException(FileNotFoundException e){
		return CommonGenericResponseUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.NOT_FOUND);
//		return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
	}
}
