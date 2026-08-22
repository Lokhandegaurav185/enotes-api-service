package com.code.endpoints;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.code.dto.LoginRequest;

import com.code.dto.UserDTO;


import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("/api/v1/auth")
public interface UserAuthControllerEndpoint {
	
	@PostMapping("/save")
	public ResponseEntity<?> register(@RequestBody UserDTO userDto, HttpServletRequest request) throws Exception;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception;
}
