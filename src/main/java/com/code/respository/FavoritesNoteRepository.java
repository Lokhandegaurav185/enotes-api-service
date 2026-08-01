package com.code.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.code.entity.FavouriteNotes;

public interface FavoritesNoteRepository extends JpaRepository<FavouriteNotes, Integer>{

	List<FavouriteNotes> findByUserId(int userId);
	
}
