package com.code.endpoints;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.code.dto.PasswordChangeRequest;


@RequestMapping("/api/v1/user")
public interface UserControllerEndpoint {
	
	@GetMapping("/profile")
	public ResponseEntity<?> getUserProfile();
	
	@PostMapping("/changePassword")
	public ResponseEntity<?> changePassword(@RequestBody PasswordChangeRequest passwordRequest);
}
