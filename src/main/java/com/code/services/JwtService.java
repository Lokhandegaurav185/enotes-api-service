package com.code.services;

import com.code.entity.User;

public interface JwtService {
	
	public String generateToken(User user);
}
