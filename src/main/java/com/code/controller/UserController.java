package com.code.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.code.dto.PasswordChangeRequest;
import com.code.dto.UserDTO;
import com.code.dto.UserResponse;
import com.code.entity.User;
import com.code.services.UserService;
import com.code.util.CommonGenericResponseUtil;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/profile")
	public ResponseEntity<?> getUserProfile(){
		User loggedInUser = CommonGenericResponseUtil.getLoggedInUser();
		UserResponse userResponse=mapper.map(loggedInUser, UserResponse.class);
		return CommonGenericResponseUtil.createBuildResponse(userResponse, HttpStatus.OK);
	}
	
	@PostMapping("/changePassword")
	public ResponseEntity<?> changePassword(@RequestBody PasswordChangeRequest passwordRequest){
		userService.changePassword(passwordRequest);
		return CommonGenericResponseUtil.createBuildResponseMessage("Change Password Successful", HttpStatus.OK);
	}		
}
