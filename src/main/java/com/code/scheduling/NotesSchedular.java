package com.code.scheduling;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.code.entity.Notes;
import com.code.respository.NotesRepository;

@Component
public class NotesSchedular {
	
	@Autowired
	private NotesRepository notesRepository;
	
	@Scheduled(cron = "0 0 0 * * ?")
	public void deleteNoteSchedular() {
		LocalDateTime cutOffDays = LocalDateTime.now().minusDays(7);
		List<Notes> deleteNotes=notesRepository.findAllByIsDeletedAndDeletedOnBefore(true,cutOffDays);
		notesRepository.deleteAll(deleteNotes);
	}
}
