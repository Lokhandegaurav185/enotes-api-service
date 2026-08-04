package com.code.util;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.code.dto.CategoryDTO;
import com.code.dto.TodoDTO;
import com.code.dto.TodoDTO.StatusDto;
import com.code.enums.TodoStatus;
import com.code.exception.DtoValidationException;
import com.code.exception.ResourceNotFoundException;

@Component
public class Validation {
	
	public void categoryValidation(CategoryDTO categoryDTO) {
		Map<String, Object> error  = new LinkedHashMap<>();
		
		if(ObjectUtils.isEmpty(categoryDTO)) {
			throw new IllegalArgumentException("category Object/JSON should not be null");
		}
		else {
			if(ObjectUtils.isEmpty(categoryDTO.getName())) {
				error.put("name", "name field is not empty");
			}
			else {
				if(categoryDTO.getName().length()<10) {
					error.put("name", "name length min 10");
				}
				if(categoryDTO.getName().length()>100) {
					error.put("name", "name length max 10");
				}
			}
			if(ObjectUtils.isEmpty(categoryDTO.getDescription())) {
				error.put("description", "description field is empty or null");
			}
			
			if(ObjectUtils.isEmpty(categoryDTO.getIsActive())) {
				error.put("isActive", "isActive field is empty or null");
			}
			else {
				if(categoryDTO.getIsActive() != Boolean.TRUE.booleanValue() 
						&& categoryDTO.getIsActive()!=Boolean.FALSE.booleanValue()) {
					error.put("isActive", "Invalid value isActive field");
				}
			
			}
		}
		if(!error.isEmpty()) {
			throw new DtoValidationException(error);
		}
	}
	
	public void todoValidation(TodoDTO todoDTO) throws Exception {
		StatusDto status = todoDTO.getStatus();
		Boolean statusFound = false;
		for(TodoStatus st: TodoStatus.values()) {
			if(st.getId().equals(status.getId())) {
				statusFound=true;
			}
		}
		if(!statusFound) {
			throw new ResourceNotFoundException("Invalid Status");
		}
	}
}
