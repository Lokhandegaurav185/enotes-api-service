package com.code.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.code.dto.CategoryDTO;
import com.code.dto.CategoryResponse;
import com.code.endpoints.CategoryControllerEndpoint;
import com.code.entity.Category;
import com.code.services.CategoryService;
import com.code.util.CommonGenericResponseUtil;

@RestController
public class CategoryController implements CategoryControllerEndpoint{
	@Autowired
	public CategoryService categoryService;
	
	@Override
	public ResponseEntity<?> saveCategory(@RequestBody CategoryDTO categoryDTO){
		Boolean saveCategory=categoryService.saveCategory(categoryDTO);
		if(saveCategory) {
			return CommonGenericResponseUtil.createBuildResponseMessage("Saved Successfully", HttpStatus.CREATED);
//			return new ResponseEntity<>("Saved Successfully",HttpStatus.CREATED);
		}
		else {
			return CommonGenericResponseUtil.createErrorResponseMessage("Not Saved", HttpStatus.INTERNAL_SERVER_ERROR);
			//return new ResponseEntity<>("Not Saved",HttpStatus.INTERNAL_SERVER_ERROR);	
		}
	}
	
	@Override
	public ResponseEntity<?> getAllCategory(){
		List<CategoryDTO> allCategory = categoryService.getAllCategory();
		if(CollectionUtils.isEmpty(allCategory)) {
			return ResponseEntity.noContent().build();
		}
		else {
			return CommonGenericResponseUtil.createBuildResponse(allCategory, HttpStatus.OK);
//			return new ResponseEntity<>(allCategory,HttpStatus.OK);	
		}
	}
	
	@Override
	public ResponseEntity<?> getActiveCategory(){
		List<CategoryResponse> allCategory = categoryService.getActiveCategory();
		if(CollectionUtils.isEmpty(allCategory)) {
			return ResponseEntity.noContent().build();
		}
		else {
			return CommonGenericResponseUtil.createBuildResponse(allCategory, HttpStatus.OK);
//			return new ResponseEntity<>(allCategory,HttpStatus.OK);	
		}
	}
	@Override
	public ResponseEntity<?> getCategoryByID(@PathVariable Integer id){
		CategoryDTO categoryDTO=categoryService.getCategoryByID(id);
		if(ObjectUtils.isEmpty(categoryDTO)) {
			return CommonGenericResponseUtil.createErrorResponseMessage("Category Not Found with id", HttpStatus.NOT_FOUND);
//			return new ResponseEntity<>("Category Not Found with id=" +id,HttpStatus.NOT_FOUND);
		}
		return CommonGenericResponseUtil.createBuildResponse(categoryDTO, HttpStatus.OK);
//		return new ResponseEntity<>(categoryDTO,HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> deleteCategoryByID(@PathVariable Integer id){
		boolean deleted=categoryService.deleteCategoryByID(id);
		if(deleted) {
			return CommonGenericResponseUtil.createBuildResponseMessage("Category deleted", HttpStatus.OK);
//			return new ResponseEntity<>("Category deleted",HttpStatus.OK);
		}
		return CommonGenericResponseUtil.createErrorResponseMessage("Not Deleted", HttpStatus.INTERNAL_SERVER_ERROR);
//		return new ResponseEntity<>("Not Deleted",HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
