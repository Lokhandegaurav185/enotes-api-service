package com.code.services;

import com.code.dto.PasswordChangeRequest;

public interface UserService {

	public void changePassword(PasswordChangeRequest changeRequest);
}
