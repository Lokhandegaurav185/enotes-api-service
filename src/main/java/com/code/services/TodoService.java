package com.code.services;

import java.util.List;

import com.code.dto.TodoDTO;


public interface TodoService {
	
	public boolean saveTodo(TodoDTO todoDto) throws Exception;
	
	public TodoDTO getTodoById(Integer id) throws Exception;

	public List<TodoDTO> getTodoByUser();
}
