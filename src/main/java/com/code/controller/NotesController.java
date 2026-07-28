package com.code.controller;

import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.code.dto.NotesDTO;
import com.code.entity.FileDetails;
import com.code.services.NotesServices;
import com.code.util.CommonGenericResponseUtil;

@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {
	
	@Autowired
	private NotesServices notesServices;
	
	@PostMapping("/save")
	public ResponseEntity<?> saveNotes(@RequestParam String notes, @RequestParam(required = false) MultipartFile file ) throws Exception{
		Boolean saveNotes = notesServices.saveNotes(notes,file);
		if(saveNotes) {
			return CommonGenericResponseUtil.createBuildResponseMessage("Note save", HttpStatus.CREATED);
		}
		return CommonGenericResponseUtil.createErrorResponseMessage("Note save", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/download/{id}")
	public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception{
		FileDetails fileDetails = notesServices.getFileDetails(id);
		byte[] downloadFile=notesServices.downloadFile(fileDetails);
		
		HttpHeaders headers = new HttpHeaders();
		String contentType=CommonGenericResponseUtil.getContentType(fileDetails.getOriginalFileName());
		headers.setContentType(MediaType.parseMediaType(contentType));//parseMediaType(contentType)
		headers.setContentDispositionFormData("attachment",fileDetails.getOriginalFileName());
		return ResponseEntity.ok().headers(headers).body(downloadFile);
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
