package com.code.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.code.dto.NotesDTO;


public interface NotesServices {
	
	public Boolean saveNotes(String notes, MultipartFile file) throws Exception;
	
	public List<NotesDTO> getAllNotes();
}
