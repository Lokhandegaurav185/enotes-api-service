package com.code.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.code.entity.Notes;

public interface NotesRepository extends JpaRepository<Notes, Integer>{

}
