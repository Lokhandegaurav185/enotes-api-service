package com.code.endpoints;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;



@RequestMapping("/api/v1/notes")
public interface NotesControllerEndpoint {
	@PostMapping("/save")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> saveNotes(@RequestParam String notes, @RequestParam(required = false) MultipartFile file ) throws Exception;
	
	@GetMapping("/download/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception;
	
	@GetMapping("/")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getAllNotes();
	
	@GetMapping("/user-notes")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getAllNotesByUser(@RequestParam(name="pageNo",defaultValue = "0")Integer pageNo,
			@RequestParam(name="pageNo",defaultValue = "5") Integer pageSize);
	
	@GetMapping("/search")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getNotesSearchByUser(@RequestParam(name="key",defaultValue = "")String keyword,
												@RequestParam(name="pageNo",defaultValue = "0")Integer pageNo,
												@RequestParam(name="pageNo",defaultValue = "5") Integer pageSize);
	
	@GetMapping("/delete/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception;
	
	@GetMapping("/restore/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception;
	
	@GetMapping("/recycle-bin")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getUserRecycleBinNotes() throws Exception;
	
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception;
	
	@DeleteMapping("/delete")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> deleteRecycleBinNotes(@PathVariable Integer id) throws Exception;
	
	@GetMapping("/favorite/{noteId}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> favoritesNotes(@PathVariable Integer noteId) throws Exception;
	
	@DeleteMapping("/unfavorite/{favoriteNoteId}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> unFavoritesNotes(@PathVariable Integer favoriteNoteId) throws Exception;
	
	@GetMapping("/favNote")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getfavoriteNote() throws Exception;
	
	@GetMapping("/copyNote/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> copyNote(@PathVariable Integer id) throws Exception;
}
