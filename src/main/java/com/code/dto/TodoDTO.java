package com.code.dto;

import java.util.Date;
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
public class TodoDTO {
    
	private Integer id;
	
	private String title;
	
	private StatusDto status;
	
	private Integer createdBy;

	private Date createdOn;
	
	private Integer updateBy;
	
	private Date updateOn;
	
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class StatusDto{
		private Integer id;
		private String name;
	}
}
