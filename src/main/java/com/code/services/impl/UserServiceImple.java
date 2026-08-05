package com.code.services.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.code.dto.EmailRequest;
import com.code.dto.UserDTO;
import com.code.entity.Role;
import com.code.entity.User;
import com.code.respository.RoleRepository;
import com.code.respository.UserRepository;
import com.code.services.UserService;
import com.code.util.Validation;
@Service
public class UserServiceImple implements UserService{
	
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
	
	public Boolean register(UserDTO userDTO) throws Exception {
		//check validation
		validation.userValidation(userDTO);
		User user = mapper.map(userDTO, User.class);
		setRole(userDTO,user);
		User save = userRepository.save(user);
		if(!ObjectUtils.isEmpty(save)) {
			emailSend(save);
			return true;
		}
		return false;
	}

	private void emailSend(User save) throws Exception {
		String message = "Hi,<b>"+save.getFirstName()+"</b>"
				         +"<br> Your Account register successfully.<br>"
				         +"<br>click the below link to verify & active your account.<br>"
				         +"<a href='#'>click Here</a> <br><br>"
				         +"Thanks,<br> Enotes.com";
		
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

}
