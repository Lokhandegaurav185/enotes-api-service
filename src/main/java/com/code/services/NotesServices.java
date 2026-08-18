package com.code.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.code.dto.FavoritesNotesDTO;
import com.code.dto.NotesDTO;
import com.code.dto.NotesResponse;
import com.code.entity.FileDetails;


public interface NotesServices {
	
	public Boolean saveNotes(String notes, MultipartFile file) throws Exception;
	
	public List<NotesDTO> getAllNotes();

	public byte[] downloadFile(FileDetails filedtls) throws Exception;
	
	public FileDetails getFileDetails(Integer id) throws Exception;

	public NotesResponse getAllNotesByUser(Integer pageNo, Integer pageSize);

	public void softDeleteNotes(Integer id) throws Exception;

	public void restoreNotes(Integer id) throws Exception ;

	public List<NotesDTO> getUserRecycleBinNotes();

	public void hardDeleteNotes(Integer id) throws Exception;

	public void deleteRecycleBin();
	
	public void favoriteNotes(Integer noteId) throws Exception;

	public void unfavoriteNotes(Integer noteId) throws Exception;
	
	public List<FavoritesNotesDTO> getUserFavoriteNotes();

	public Boolean copyNotes(Integer id) throws Exception;
	
}
