package com.code.services.impl;

import java.util.UUID;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.code.dto.EmailRequest;
import com.code.dto.PasswordChangeRequest;
import com.code.dto.PasswordResetRequest;
import com.code.entity.User;
import com.code.exception.ResourceNotFoundException;
import com.code.respository.UserRepository;
import com.code.services.UserService;
import com.code.util.CommonGenericResponseUtil;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserServiceImple implements UserService{

    private final EmailService emailService;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;

    UserServiceImple(EmailService emailService) {
        this.emailService = emailService;
    }
	
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

	@Override
	public void sendEmailPasswordReset(String email,HttpServletRequest request) throws Exception {
		User user = userRepository.findByEmail(email);
		if(ObjectUtils.isEmpty(user)) {
			throw new ResourceNotFoundException("Invalid Email");
		}
		//generate unique password token
		String passwordResetToken = UUID.randomUUID().toString();
		user.getStatus().setPasswordResetToken(passwordResetToken);
		User updateUser = userRepository.save(user);
		String url = CommonGenericResponseUtil.getUrl(request);
	    sendEmailRequest(updateUser,url);
		
		
	}

	private void sendEmailRequest(User user,String url) throws Exception {
		String message = "Hi,<b>[[username]]</b>"
		         +"<br> You have to requested to reset your password.<br>"
		         +"<br>click the below link to Change your password<br>"
		         +"<a href='[[url]]'>Change password</a> <br><br>"
		         +"<br>Ignore to this mail if you do remember your password<br>"
		         +"Thanks,<br> Enotes.com";

message = message.replace("[[username]]", user.getFirstName());
message = message.replace("[[url]]", url+"/api/v1/home/verifyPasswordLink?uid="+user.getId()+"&&code="+user.getStatus().getPasswordResetToken());

EmailRequest emailRequest = EmailRequest.builder()
							.to(user.getEmail())
							.title("Password Reset")
							.subject("Password Reset Link")
							.message(message)
							.build();
	//send email to password reset request
	emailService.sendEmail(emailRequest);
	}

	@Override
	public void verifyPasswordResetLink(Integer uid, String code) throws Exception {
		User user=userRepository.findById(uid).orElseThrow(()->new ResourceNotFoundException("Invalid User"));
		verifyPasswordResetToken(user.getStatus().getPasswordResetToken(),code);
		
	}

	private void verifyPasswordResetToken(String existToken, String reqToken) {
//		//request token not null
//		if(StringUtils.hasText(existToken)){
//			
//			//password already reset
//			if(!StringUtils.hasText(reqToken)){
//				throw new IllegalArgumentException("Already password reset");
//			}
//			
//			//user request token change
//			if(!existToken.equals(reqToken)){
//				throw new IllegalArgumentException("invalid URL");
//			}
//			
//		}else {
//			throw new IllegalArgumentException("invalid Token");
//		}
		
		// Token does not exist in database
	    if (!StringUtils.hasText(existToken)) {
	        throw new IllegalArgumentException("Already password reset");
	    }

	    // Token does not exist in request
	    if (!StringUtils.hasText(reqToken)) {
	        throw new IllegalArgumentException("Invalid Token");
	    }

	    // Token doesn't match
	    if (!existToken.equals(reqToken)) {
	        throw new IllegalArgumentException("Invalid URL");
	    }
	}

	@Override
	public void resetPassword(PasswordResetRequest passwordResetRequest) throws Exception {
		User user=userRepository.findById(passwordResetRequest.getUid()).orElseThrow(()->new ResourceNotFoundException("Invalid User"));
		
		String encodePassword = passwordEncoder.encode(passwordResetRequest.getNewPassword());
		user.setPassword(encodePassword);
		user.getStatus().setPasswordResetToken(null);
		userRepository.save(user);
	}	
}
