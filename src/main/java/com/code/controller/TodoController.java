package com.code.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.code.dto.TodoDTO;
import com.code.endpoints.TodoControllerEndpoint;
import com.code.entity.Todo;
import com.code.services.TodoService;
import com.code.util.CommonGenericResponseUtil;

@RestController
public class TodoController implements TodoControllerEndpoint{
	
	@Autowired
	private TodoService todoService;
	
	@Override
	public ResponseEntity<?> saveTodo(@RequestBody TodoDTO todo ) throws Exception{
		Boolean saveTodo = todoService.saveTodo(todo);
		if(saveTodo) {
			return CommonGenericResponseUtil.createBuildResponseMessage("Todo save", HttpStatus.CREATED);
		}
		return CommonGenericResponseUtil.createErrorResponseMessage("Todo Not save", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<?> getTodoById(@PathVariable Integer id ) throws Exception{
		 TodoDTO todoById = todoService.getTodoById(id);
		
		return CommonGenericResponseUtil.createBuildResponse(todoById, HttpStatus.OK);
		
		
	}
	
	@Override
	public ResponseEntity<?> getTodoByUser(){
		List<TodoDTO> ListTodo = todoService.getTodoByUser();
		if(CollectionUtils.isEmpty(ListTodo)) {
			return ResponseEntity.noContent().build();
		}
		return CommonGenericResponseUtil.createErrorResponse(ListTodo, HttpStatus.OK);
	}
}
