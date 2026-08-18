package com.code.dto;

import java.util.List;

import com.code.entity.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
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
public class UserResponse {
	
	private Integer id;
	
	private String firstName;

	private String lastName;

	private String email;
	
	private String mobNo;
	
	private StatusDto statusDto;
	
	List<RoleDto> roles;
	
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class RoleDto{
		private Integer id;
		
		private String name;
	}
	
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
