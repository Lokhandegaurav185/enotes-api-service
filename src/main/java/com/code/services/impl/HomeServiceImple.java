package com.code.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.code.entity.AccountStatus;
import com.code.entity.User;
import com.code.exception.ResourceNotFoundException;
import com.code.exception.SuccessException;
import com.code.respository.UserRepository;
import com.code.services.HomeService;

@Service
public class HomeServiceImple implements HomeService{

	@Autowired
	private UserRepository userRepository;
	
	
	public Boolean verifyAccount(Integer userid, String verificationCode) throws Exception {
		User user = userRepository.findById(userid).orElseThrow(()-> new ResourceNotFoundException("Invalid user"));
		
		System.out.println("DB Code : " + user.getStatus().getVerificationCode());
	    System.out.println("URL Code: " + verificationCode);
		if(user.getStatus().getVerificationCode()==null) {
			throw new SuccessException("Account already verified");
		}
		if(user.getStatus().getVerificationCode().equals(verificationCode)) {
			AccountStatus status = user.getStatus();
			status.setIsActive(true);
			status.setVerificationCode(null);
			userRepository.save(user);
			return true;
		}
		 System.out.println("Returning false");
		return false;
	}

}
