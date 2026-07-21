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

import com.code.dto.NotesDTO;
import com.code.services.NotesServices;
import com.code.validation.CommonGenericResponseUtil;

@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {
	
	@Autowired
	private NotesServices notesServices;
	
	@PostMapping("/save")
	public ResponseEntity<?> saveNotes(@RequestBody NotesDTO notesDTO) throws Exception{
		Boolean saveNotes = notesServices.saveNotes(notesDTO);
		if(saveNotes) {
			return CommonGenericResponseUtil.createBuildResponseMessage("Note save", HttpStatus.CREATED);
		}
		return CommonGenericResponseUtil.createErrorResponseMessage("Note save", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/")
	public ResponseEntity<?> getAllNotes(){
		List<NotesDTO> getNotes = notesServices.getAllNotes();
		if(CollectionUtils.isEmpty(getNotes)) {
			return ResponseEntity.noContent().build();
		}
		return CommonGenericResponseUtil.createBuildResponse(getNotes, HttpStatus.OK);
	}
}
