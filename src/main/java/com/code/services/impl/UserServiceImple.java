package com.code.services.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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
	
	public Boolean register(UserDTO userDTO) {
		//check validation
		validation.userValidation(userDTO);
		User user = mapper.map(userDTO, User.class);
		setRole(userDTO,user);
		User save = userRepository.save(user);
		if(!ObjectUtils.isEmpty(save)) {
			return true;
		}
		return false;
	}

	private void setRole(UserDTO userDTO, User user) {
		List<Integer> requestId = userDTO.getRoles().stream().map(r -> r.getId()).toList();
		List<Role> roles = roleRepository.findAllById(requestId);
		user.setRoles(roles);
	}

}
