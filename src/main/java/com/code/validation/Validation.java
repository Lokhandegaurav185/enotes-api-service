package com.code.validation;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.code.dto.CategoryDTO;
import com.code.exception.DtoValidationException;

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
}
