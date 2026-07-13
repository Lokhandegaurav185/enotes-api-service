package com.code.services.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.code.entity.Category;
import com.code.respository.CategoryRepository;
import com.code.services.CategoryService;

@Service
public class CategoryServiceImple implements CategoryService{
	
	@Autowired
	public CategoryRepository categoryRepository;
	
	public Boolean saveCategory(Category category) {
		category.setIsDeleted(false);
		category.setCreatedBy(1);
		category.setCreatedOn(new Date());
		Category saveCategory = categoryRepository.save(category);
		if(ObjectUtils.isEmpty(saveCategory)) {
			return false;
		}
		return true;
	}

	public List<Category> getAllCategory() {
		List<Category> getAllCat = categoryRepository.findAll();
		return getAllCat;
	}

}
