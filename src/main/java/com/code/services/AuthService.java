package com.code.services;

import com.code.dto.LoginRequest;
import com.code.dto.LoginResponse;
import com.code.dto.UserDTO;

public interface AuthService {

	public Boolean register(UserDTO userDTO, String url) throws Exception;

	public LoginResponse login(LoginRequest loginRequest);

	

	
}
