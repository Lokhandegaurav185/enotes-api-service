package com.code.exception;

import java.util.Map;

public class DtoValidationException extends RuntimeException{
	Map<String, Object> error;

	public DtoValidationException(Map<String, Object> error) {
		super();
		this.error = error;
	}
	
	public Map<String, Object> getErrors(){
		return error;
	}
}
