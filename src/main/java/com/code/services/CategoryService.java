package com.code.services;

import java.util.List;

import com.code.entity.Category;

public interface CategoryService {
	public Boolean saveCategory(Category category);
	
	public List<Category> getAllCategory();
}
