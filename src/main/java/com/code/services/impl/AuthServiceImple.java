package com.code.services.impl;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.code.config.security.CustomUserDetails;
import com.code.dto.EmailRequest;
import com.code.dto.LoginRequest;
import com.code.dto.LoginResponse;
import com.code.dto.UserDTO;
import com.code.dto.UserResponse;
import com.code.entity.AccountStatus;
import com.code.entity.Role;
import com.code.entity.User;
import com.code.respository.RoleRepository;
import com.code.respository.UserRepository;
import com.code.services.JwtService;
import com.code.services.AuthService;
import com.code.util.Validation;
@Service
public class AuthServiceImple implements AuthService{
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private Validation validation;

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtService jwtService;
	
	public Boolean register(UserDTO userDTO, String url) throws Exception {
		//check validation
		validation.userValidation(userDTO);
		User user = mapper.map(userDTO, User.class);
		setRole(userDTO,user);
		
		AccountStatus status = AccountStatus.builder()
								.isActive(false)
								.verificationCode(UUID.randomUUID().toString())
								.build();
		user.setStatus(status);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		User save = userRepository.save(user);
		if(!ObjectUtils.isEmpty(save)) {
			emailSend(save,url);
			return true;
		}
		return false;
	}

	private void emailSend(User save,String url) throws Exception {
		String message = "Hi,<b>[[username]]</b>"
				         +"<br> Your Account register successfully.<br>"
				         +"<br>click the below link to verify & active your account.<br>"
				         +"<a href='[[url]]'>click Here</a> <br><br>"
				         +"Thanks,<br> Enotes.com";
		
		message = message.replace("[[username]]", save.getFirstName());
		message = message.replace("[[url]]", url+"/api/v1/home/verify?uid="+save.getId()+"&&code="+save.getStatus().getVerificationCode());
		
		EmailRequest emailRequest = EmailRequest.builder()
									.to(save.getEmail())
									.title("account creating confirmation")
									.subject("Account create success")
									.message(message)
									.build();
		emailService.sendEmail(emailRequest);
	}

	private void setRole(UserDTO userDTO, User user) {
		List<Integer> requestId = userDTO.getRoles().stream().map(r -> r.getId()).toList();
		List<Role> roles = roleRepository.findAllById(requestId);
		user.setRoles(roles);
	}

	@Override
	public LoginResponse login(LoginRequest loginRequest) {
		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		if(authenticate.isAuthenticated()) {
			CustomUserDetails customUserDetails = (CustomUserDetails)authenticate.getPrincipal();
			String token = jwtService.generateToken(customUserDetails.getUser());
			LoginResponse loginResponse = LoginResponse.builder()
					.user(mapper.map(customUserDetails.getUser(), UserResponse.class))
					.token(token)
					.build();
			return loginResponse;
		}	
		return null;
	}

}
