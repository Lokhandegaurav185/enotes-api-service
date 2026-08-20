package com.code.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.code.dto.PasswordResetRequest;
import com.code.services.HomeService;
import com.code.services.UserService;
import com.code.util.CommonGenericResponseUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {
	
	@Autowired
	private HomeService homeService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/verify")
	public ResponseEntity<?> register(@RequestParam Integer uid, @RequestParam String code ) throws Exception{
		Boolean verifyAccount = homeService.verifyAccount(uid,code);
		if(verifyAccount) {
			return CommonGenericResponseUtil.createBuildResponseMessage("Account verification success", HttpStatus.OK);
		}
		return CommonGenericResponseUtil.createErrorResponseMessage("Invalid verification Link", HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/sendEmailReset")
	public ResponseEntity<?> sendEmailForPasswordReset(@RequestParam String email, HttpServletRequest request) throws Exception {
		userService.sendEmailPasswordReset(email,request);
		return CommonGenericResponseUtil.createBuildResponseMessage("Send Email success!! check email for reset password", HttpStatus.OK);
	}
	
	@GetMapping("/verifyPasswordLink")
	public ResponseEntity<?> verifyPasswordResetLink(@RequestParam Integer uid,@RequestParam String code) throws Exception {
		userService.verifyPasswordResetLink(uid,code);
		return CommonGenericResponseUtil.createBuildResponseMessage("verification success", HttpStatus.OK);
		
	}
	@PostMapping("/resetPassword")
	public ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest passwordResetRequest) throws Exception {
		userService.resetPassword(passwordResetRequest);
		return CommonGenericResponseUtil.createBuildResponseMessage("Password Request success", HttpStatus.OK);
	}

	
}
