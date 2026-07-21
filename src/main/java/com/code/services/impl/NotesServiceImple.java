package com.code.services.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.code.dto.NotesDTO;
import com.code.dto.NotesDTO.CategoryDTO;
import com.code.entity.Notes;
import com.code.exception.ResourceNotFoundException;
import com.code.respository.CategoryRepository;
import com.code.respository.NotesRepository;
import com.code.services.NotesServices;

@Service
public class NotesServiceImple implements NotesServices{

	@Autowired
	private NotesRepository notesRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public Boolean saveNotes(NotesDTO notesDTO) throws Exception {
		//validation
		checkCategoryExist(notesDTO.getCategory());
		
		Notes notes = mapper.map(notesDTO, Notes.class);
		Notes saveNotes = notesRepository.save(notes);
		if(!ObjectUtils.isEmpty(saveNotes)) {
			return true;
		}
		return false;
	}


	private void checkCategoryExist(CategoryDTO category) throws Exception {
		categoryRepository.findById(category.getId()).orElseThrow(()->new ResourceNotFoundException("Category id invalid"));
		
	}


	public List<NotesDTO> getAllNotes() {
		return notesRepository.findAll().stream()
				.map(note -> mapper.map(note, NotesDTO.class)).toList();
	}

}
