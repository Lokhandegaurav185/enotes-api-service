package com.code.controller;

import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.code.dto.FavoritesNotesDTO;
import com.code.dto.NotesDTO;
import com.code.dto.NotesResponse;
import com.code.endpoints.NotesControllerEndpoint;
import com.code.entity.FileDetails;
import com.code.services.NotesServices;
import com.code.util.CommonGenericResponseUtil;

@RestController
public class NotesController implements NotesControllerEndpoint{
	
	@Autowired
	private NotesServices notesServices;
	
	@Override
	public ResponseEntity<?> saveNotes(@RequestParam String notes, @RequestParam(required = false) MultipartFile file ) throws Exception{
		Boolean saveNotes = notesServices.saveNotes(notes,file);
		if(saveNotes) {
			return CommonGenericResponseUtil.createBuildResponseMessage("Note save", HttpStatus.CREATED);
		}
		return CommonGenericResponseUtil.createErrorResponseMessage("Note save", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Override
	public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception{
		FileDetails fileDetails = notesServices.getFileDetails(id);
		byte[] downloadFile=notesServices.downloadFile(fileDetails);
		
		HttpHeaders headers = new HttpHeaders();
		String contentType=CommonGenericResponseUtil.getContentType(fileDetails.getOriginalFileName());
		headers.setContentType(MediaType.parseMediaType(contentType));//parseMediaType(contentType)
		headers.setContentDispositionFormData("attachment",fileDetails.getOriginalFileName());
		return ResponseEntity.ok().headers(headers).body(downloadFile);
	}
	
	@Override
	public ResponseEntity<?> getAllNotes(){
		List<NotesDTO> getNotes = notesServices.getAllNotes();
		if(CollectionUtils.isEmpty(getNotes)) {
			return ResponseEntity.noContent().build();
		}
		return CommonGenericResponseUtil.createBuildResponse(getNotes, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> getAllNotesByUser(@RequestParam(name="pageNo",defaultValue = "0")Integer pageNo,
			@RequestParam(name="pageNo",defaultValue = "5") Integer pageSize){
		NotesResponse getNotes = notesServices.getAllNotesByUser(pageNo,pageSize);
//		if(CollectionUtils.isEmpty(getNotes)) {
//			return ResponseEntity.noContent().build();
//		}
		return CommonGenericResponseUtil.createBuildResponse(getNotes, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> getNotesSearchByUser(@RequestParam(name="key",defaultValue = "")String keyword,
												@RequestParam(name="pageNo",defaultValue = "0")Integer pageNo,
												@RequestParam(name="pageNo",defaultValue = "5") Integer pageSize){
		NotesResponse getNotes = notesServices.getSearchUserNotes(pageNo,pageSize,keyword);
//		if(CollectionUtils.isEmpty(getNotes)) {
//			return ResponseEntity.noContent().build();
//		}
		return CommonGenericResponseUtil.createBuildResponse(getNotes, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception{
		 notesServices.softDeleteNotes(id);
		
		return CommonGenericResponseUtil.createBuildResponseMessage("Note deleted successfully", HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception{
		notesServices.restoreNotes(id);
		
		return CommonGenericResponseUtil.createBuildResponseMessage("Note restore successfully", HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> getUserRecycleBinNotes() throws Exception{
		List<NotesDTO> notes=notesServices.getUserRecycleBinNotes();
		if(CollectionUtils.isEmpty(notes)) {
			return CommonGenericResponseUtil.createBuildResponseMessage("Notes Not Available in RecycleBin", HttpStatus.OK);
		}
		return CommonGenericResponseUtil.createBuildResponse(notes, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception{
		 notesServices.hardDeleteNotes(id);
		
		return CommonGenericResponseUtil.createBuildResponseMessage("Note deleted successfully", HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> deleteRecycleBinNotes(@PathVariable Integer id) throws Exception{
		
		notesServices.deleteRecycleBin();
		
		return CommonGenericResponseUtil.createBuildResponseMessage("Note deleted successfully", HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> favoritesNotes(@PathVariable Integer noteId) throws Exception{
		notesServices.favoriteNotes(noteId);
		return CommonGenericResponseUtil.createBuildResponseMessage("Fav Note add successfully", HttpStatus.CREATED);
	}
	
	@Override
	public ResponseEntity<?> unFavoritesNotes(@PathVariable Integer favoriteNoteId) throws Exception{
		notesServices.unfavoriteNotes(favoriteNoteId);
		
		return CommonGenericResponseUtil.createBuildResponseMessage("fav Note remove successfully", HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> getfavoriteNote() throws Exception{
		List<FavoritesNotesDTO> userFavoriteNotes = notesServices.getUserFavoriteNotes();
		if(CollectionUtils.isEmpty(userFavoriteNotes)) {
			return ResponseEntity.noContent().build();
		}
		return CommonGenericResponseUtil.createBuildResponse(userFavoriteNotes, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> copyNote(@PathVariable Integer id) throws Exception{
		Boolean copyNote=notesServices.copyNotes(id);
		if(copyNote) {
			return  CommonGenericResponseUtil.createBuildResponseMessage("Copy success", HttpStatus.OK);
		}
		return CommonGenericResponseUtil.createBuildResponseMessage("Copy Failed", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
