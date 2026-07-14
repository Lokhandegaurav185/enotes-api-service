package com.code.services.impl;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.code.dto.CategoryDTO;
import com.code.dto.CategoryResponse;
import com.code.entity.Category;
import com.code.respository.CategoryRepository;
import com.code.services.CategoryService;

@Service
public class CategoryServiceImple implements CategoryService{
	
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ModelMapper mapper;
	
	public Boolean saveCategory(CategoryDTO categoryDTO) {
//		Category category = new Category();
//		category.setName(categoryDTO.getName());
//		category.setDescription(categoryDTO.getDescription());
//		category.setIsActive(categoryDTO.getIsActive());
		Category category = mapper.map(categoryDTO, Category.class);
		category.setIsDeleted(false);
		category.setCreatedBy(1);
		category.setCreatedOn(new Date());
		Category saveCategory = categoryRepository.save(category);
		if(ObjectUtils.isEmpty(saveCategory)) {
			return false;
		}
		return true;
	}

	public List<CategoryDTO> getAllCategory() {
		List<Category> getAllCat = categoryRepository.findAll();
		List<CategoryDTO> categoryDTOList = getAllCat.stream().map(cat->mapper.map(cat, CategoryDTO.class)).toList();
		return categoryDTOList	;
	}
	
	public List<CategoryResponse> getActiveCategory() {
		List<Category> categories = categoryRepository.findByIsActiveTrue();
		List<CategoryResponse> categoryDTOList =  categories.stream().map(cat->mapper.map(cat, CategoryResponse.class)).toList();
		return	categoryDTOList;
	}

}
