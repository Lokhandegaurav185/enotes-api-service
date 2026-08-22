package com.code.endpoints;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.code.dto.PasswordResetRequest;


import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("/api/v1/home")
public interface HomeControllerEndpoint {
	
	@GetMapping("/verify")
	public ResponseEntity<?> register(@RequestParam Integer uid, @RequestParam String code ) throws Exception;
	
	@GetMapping("/sendEmailReset")
	public ResponseEntity<?> sendEmailForPasswordReset(@RequestParam String email, HttpServletRequest request) throws Exception; 
	
	@GetMapping("/verifyPasswordLink")
	public ResponseEntity<?> verifyPasswordResetLink(@RequestParam Integer uid,@RequestParam String code) throws Exception; 
	
	@PostMapping("/resetPassword")
	public ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest passwordResetRequest) throws Exception;
}
