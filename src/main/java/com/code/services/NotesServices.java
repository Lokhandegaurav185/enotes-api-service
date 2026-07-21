package com.code.services;

import java.util.List;

import com.code.dto.NotesDTO;


public interface NotesServices {
	
	public Boolean saveNotes(NotesDTO notesDTO) throws Exception;
	
	public List<NotesDTO> getAllNotes();
}
