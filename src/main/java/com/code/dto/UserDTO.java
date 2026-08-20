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
public class UserDTO {
	
	private Integer id;
	
	private String firstName;

	private String lastName;

	private String email;
	
	private String password;
	
	private String mobNo;
	
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
}
