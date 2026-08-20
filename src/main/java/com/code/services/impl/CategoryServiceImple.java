package com.code.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.code.dto.CategoryDTO;
import com.code.dto.CategoryResponse;
import com.code.entity.Category;
import com.code.exception.ExistDataException;
import com.code.respository.CategoryRepository;
import com.code.services.CategoryService;
import com.code.util.Validation;

@Service
public class CategoryServiceImple implements CategoryService{
	
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private Validation validation;
	
	private Optional<Category>  findbyId;
	
	public Boolean saveCategory(CategoryDTO categoryDTO) {
		
		//check validation first 
		validation.categoryValidation(categoryDTO);
		
		//check exist category or not
		Boolean exists = categoryRepository.existsByName(categoryDTO.getName().trim());
		
		if(exists) {
			throw new ExistDataException("Category Already Exist");
		}
		
		Category category = mapper.map(categoryDTO, Category.class);
		
		if(ObjectUtils.isEmpty(category.getId())) {
			category.setIsDeleted(false);
//			category.setCreatedBy(1);
			category.setCreatedOn(new Date());
		}
		else {
			updateCategory(category);
		}
		
		Category saveCategory = categoryRepository.save(category);
		if(ObjectUtils.isEmpty(saveCategory)) {
			return false;
		}
		return true;
	}

	private void updateCategory(Category category) {
		Optional<Category> findById = categoryRepository.findById(category.getId());
		if(findById.isPresent()) {
			Category existCategory = findById.get();
			category.setCreatedBy(existCategory.getCreatedBy());
			category.setCreatedOn(existCategory.getCreatedOn());
			category.setIsDeleted(existCategory.getIsDeleted());
//			category.setUpdateBy(1);
//			category.setUpdateOn(new Date());
		}
		
	}

	public List<CategoryDTO> getAllCategory() {
		List<Category> getAllCat = categoryRepository.findByIsDeletedFalse();
		List<CategoryDTO> categoryDTOList = getAllCat.stream().map(cat->mapper.map(cat, CategoryDTO.class)).toList();
		return categoryDTOList	;
	}
	
	public List<CategoryResponse> getActiveCategory() {
		List<Category> categories = categoryRepository.findByIsActiveTrueAndIsDeletedFalse();
		List<CategoryResponse> categoryDTOList =  categories.stream().map(cat->mapper.map(cat, CategoryResponse.class)).toList();
		return	categoryDTOList;
	}

	@Override
	public CategoryDTO getCategoryByID(Integer id) {
		Optional<Category> findbyId = categoryRepository.findByIdAndIsDeletedFalse(id);
		if(findbyId.isPresent()) {
			Category category = findbyId.get();
			return mapper.map(category,CategoryDTO.class);
		}
		return null;
	}

	@Override
	public boolean deleteCategoryByID(Integer id) {
		Optional<Category> findbyId = categoryRepository.findById(id);
		if(findbyId.isPresent()) {
			Category category = findbyId.get();
			category.setIsDeleted(true);
			categoryRepository.save(category);
			return true;
		}
		return false;
	}

}
