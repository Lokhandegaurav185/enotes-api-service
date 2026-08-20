package com.code.dto;

import com.code.entity.Notes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavoritesNotesDTO {

	private Integer id;
	
	private Notes note;
	
	private Integer userId;
	
}
