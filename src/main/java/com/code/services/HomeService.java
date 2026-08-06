package com.code.services;

public interface HomeService {

	public Boolean verifyAccount(Integer id, String verificationCode) throws Exception;
}
