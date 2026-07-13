package com.code.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.code.entity.Category;
import com.code.services.CategoryService;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
	@Autowired
	public CategoryService categoryService;
	
	@PostMapping("/saveCategory")
	public ResponseEntity<?> saveCategory(@RequestBody Category category){
		Boolean saveCategory=categoryService.saveCategory(category);
		if(saveCategory) {
			return new ResponseEntity<>("Saved Successfully",HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<>("Not Saved",HttpStatus.INTERNAL_SERVER_ERROR);	
		}
	}
	
	@GetMapping("/getAllCategory")
	public ResponseEntity<?> getAllCategory(){
		List<Category> allCategory = categoryService.getAllCategory();
		if(CollectionUtils.isEmpty(allCategory)) {
			return ResponseEntity.noContent().build();
		}
		else {
			return new ResponseEntity<>(allCategory,HttpStatus.OK);	
		}
	}
}
