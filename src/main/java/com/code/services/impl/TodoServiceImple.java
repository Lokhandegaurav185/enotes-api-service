package com.code.services.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.code.dto.TodoDTO;
import com.code.dto.TodoDTO.StatusDto;
import com.code.dto.TodoDTO.StatusDto.StatusDtoBuilder;
import com.code.entity.Todo;
import com.code.enums.TodoStatus;
import com.code.exception.ResourceNotFoundException;
import com.code.respository.TodoRepository;
import com.code.services.TodoService;
import com.code.util.Validation;

@Service
public class TodoServiceImple implements TodoService{

	
	@Autowired
	private TodoRepository todoRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private Validation validation;
	public boolean saveTodo(TodoDTO todoDto) throws Exception {
		//validation check
		validation.todoValidation(todoDto);
		
		Todo todo = mapper.map(todoDto, Todo.class);
		todo.setStatusId(todoDto.getStatus().getId());
		Todo save = todoRepository.save(todo);
		if(!ObjectUtils.isEmpty(save)) {
			return true;
		}
		return false;
	}

	

	@Override
	public TodoDTO getTodoById(Integer id) throws Exception {
		Todo todo=todoRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Todo not found ! id invalid"));
		TodoDTO todoDTO = mapper.map(todo, TodoDTO.class);
		setStatus(todoDTO,todo);
		return todoDTO;
	}
	private void setStatus(TodoDTO todoDto, Todo todo) {
		for(TodoStatus st: TodoStatus.values()) {
			
			if(st.getId().equals(todo.getStatusId())) {
				
				StatusDto builder = StatusDto.builder()
						.id(st.getId())
						.name(st.getName())
						.build();
				todoDto.setStatus(builder);
			}
		}		
	}

	@Override
	public List<TodoDTO> getTodoByUser() {
		Integer userId=2;
		List<Todo> todos=todoRepository.findByCreatedBy(userId);
		return todos.stream().map(td->mapper.map(td, TodoDTO.class)).toList();
	}

}
