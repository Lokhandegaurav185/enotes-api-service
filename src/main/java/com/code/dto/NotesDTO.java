package com.code.dto;

import java.util.Date;

import com.code.entity.FileDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotesDTO {
	
	private Integer id;
	
	private String title;
	
	private String description;
	
	private CategoryDTO category;
	
	private Integer createdBy;

	private Date createdOn;
	
	private Integer updateBy;
	
	private Date updateOn;
	
	private FileDetails details;
	
	
	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	@Setter
	public static class CategoryDTO{
		private Integer id;
		
		private String name;
	}
	
	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	@Setter
	public static class FileDetails{
		private Integer id;

		private String originalFileName;
		
		private String displayFileName;
		
	}
}
