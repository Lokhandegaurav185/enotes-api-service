package com.code.exception;

import java.io.FileNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.code.util.CommonGenericResponseUtil;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler{
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<?> handleNullPointException(Exception e){
		log.error("GlobalExceptionHandler : handleNullPointException :{}",e.getMessage());
		return CommonGenericResponseUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//		return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(SuccessException.class)
	public ResponseEntity<?> handleSuccessException(Exception e){
		log.error("GlobalExceptionHandler : handleSuccessException :{}",e.getMessage());
		return CommonGenericResponseUtil.createBuildResponseMessage(e.getMessage(), HttpStatus.OK);
//		return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(InvalidTokenException.class)
	public ResponseEntity<?> handleInvalidTokenException(Exception e){
		log.error("GlobalExceptionHandler : handleInvalidTokenException :{}",e.getMessage());
		return CommonGenericResponseUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.FORBIDDEN);
//		return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotException(Exception e){
		log.error("GlobalExceptionHandler : handleResourceNotException :{}",e.getMessage());
		return CommonGenericResponseUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.NOT_FOUND);
//		return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<?> handleIllegalArgumentException(Exception e){
		log.error("GlobalExceptionHandler : handleIllegalArgumentException :{}",e.getMessage());
		return CommonGenericResponseUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.NOT_FOUND);
//		return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(DtoValidationException.class)
	public ResponseEntity<?> handleDtoValidationException(DtoValidationException e){
		log.error("GlobalExceptionHandler : handleDtoValidationException :{}",e.getMessage());
		return CommonGenericResponseUtil.createErrorResponse(e.getErrors(), HttpStatus.BAD_REQUEST);
//		return new ResponseEntity<>(e.getErrors(),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ExistDataException.class)
	public ResponseEntity<?> handleExistDataException(ExistDataException e){
		log.error("GlobalExceptionHandler : handleExistDataException :{}",e.getMessage());
		return CommonGenericResponseUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.CONFLICT);
//		return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<?> handleMessageNotReadableException(HttpMessageNotReadableException e){
		log.error("GlobalExceptionHandler : handleMessageNotReadableException :{}",e.getMessage());
		return CommonGenericResponseUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
//		return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(FileNotFoundException.class)
	public ResponseEntity<?> handleFileNotFoundException(FileNotFoundException e){
		log.error("GlobalExceptionHandler : handleException :{}",e.getMessage());
		return CommonGenericResponseUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.NOT_FOUND);
//		return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException e){
		log.error("GlobalExceptionHandler : handleBadCredentialsException :{}",e.getMessage());
		return CommonGenericResponseUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
//		return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
	}
}
