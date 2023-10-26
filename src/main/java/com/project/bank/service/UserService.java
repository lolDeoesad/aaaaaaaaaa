package com.project.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.bank.domain.User;
import com.project.bank.domain.RoleType;
import com.project.bank.jwt.JwtService;
import com.project.bank.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	public void signup(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword())); // 비밀번호 암호화
		user.setRole(RoleType.USER);
		
		userRepository.save(user);
		
	}
	
	
	
}






