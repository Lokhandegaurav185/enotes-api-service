package com.code.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.code.services.HomeService;

import com.code.util.CommonGenericResponseUtil;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {
	
	@Autowired
	private HomeService homeService;
	
	@GetMapping("/verify")
	public ResponseEntity<?> register(@RequestParam Integer uid, @RequestParam String code ) throws Exception{
		Boolean verifyAccount = homeService.verifyAccount(uid,code);
		if(verifyAccount) {
			return CommonGenericResponseUtil.createBuildResponseMessage("Account verification success", HttpStatus.OK);
		}
		return CommonGenericResponseUtil.createErrorResponseMessage("Invalid verification Link", HttpStatus.BAD_REQUEST);
	}

	
}
