package com.code.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.code.dto.TodoDTO;
import com.code.dto.UserDTO;
import com.code.services.UserService;
import com.code.util.CommonGenericResponseUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/auth")
public class UserAuthController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/save")
	public ResponseEntity<?> register(@RequestBody UserDTO userDto, HttpServletRequest request) throws Exception{
		String url = CommonGenericResponseUtil.getUrl(request);
		Boolean save = userService.register(userDto,url);
		
		if(save) {
			return CommonGenericResponseUtil.createBuildResponseMessage("user register", HttpStatus.CREATED);
		}
		return CommonGenericResponseUtil.createErrorResponseMessage("user Not register", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
