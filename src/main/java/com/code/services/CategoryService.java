package com.code.services;

import java.util.List;

import com.code.dto.CategoryDTO;
import com.code.dto.CategoryResponse;
import com.code.entity.Category;

public interface CategoryService {
	public Boolean saveCategory(CategoryDTO categoryDTO);
	
	public List<CategoryDTO> getAllCategory();
	
	public List<CategoryResponse> getActiveCategory();
}
