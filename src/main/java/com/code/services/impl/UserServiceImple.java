package com.code.services.impl;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.code.dto.PasswordChangeRequest;
import com.code.entity.User;
import com.code.respository.UserRepository;
import com.code.services.UserService;
import com.code.util.CommonGenericResponseUtil;

@Service
public class UserServiceImple implements UserService{

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void changePassword(PasswordChangeRequest changeRequest) {
		User loggedInUser = CommonGenericResponseUtil.getLoggedInUser();
		if(!passwordEncoder.matches(changeRequest.getOldPassword(),loggedInUser.getPassword())) {
			throw new IllegalArgumentException("Old password is Incorrect !!");
		}
		String encodePassword = passwordEncoder.encode(changeRequest.getNewPassword());
		loggedInUser.setPassword(encodePassword);
		userRepository.save(loggedInUser);
	}

	
}
