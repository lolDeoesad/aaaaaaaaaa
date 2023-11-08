package com.project.bank.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	public User getUser(String username) {
		return userRepository.findByUsername(username).get();
	}
	
	public void insertUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole(RoleType.WEBUSER);
		userRepository.save(user);	
	}
	
	public void updateUser(User user, User loginUser) {
		user.setId(loginUser.getId());

		// 고정해야 할 컬럼에 대한 거 고려하면 code 추가 필요함
		// eg. username 고정 필요할 경우 아래 주석 한줄 추가해야 함!
		// user.setUsername(loginUser.getUsername());

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole(RoleType.WEBUSER);
		userRepository.save(user);
	}
	
	public void deleteUser(User user) {
		userRepository.delete(user);;	
	}
	
	public ResponseEntity<?> getResponseEntity(String username, String password) {
		UsernamePasswordAuthenticationToken upaToken = new UsernamePasswordAuthenticationToken(username, password);
		Authentication auth = authenticationManager.authenticate(upaToken);
		String jwt = jwtService.getToken(auth.getName());
		return ResponseEntity.ok()
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
						.header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization")
						.build();
	}
	
	
	public boolean hasUser(String username) {			
		return userRepository.existsByUsername(username);
	}
	
	public List<User> notUserList() {
		return userRepository.findByRole();
	}
	
	public void updateWebUserRole(List<User> webUsers) {	
		for(User user : webUsers) {
			user.setRole(RoleType.WEBUSER);
			userRepository.save(user);
		}
	}
	
	public RoleType getLoginUserRole(Authentication authentication) {
		return getUser(authentication.getName()).getRole();
	}
	public boolean hasRole(RoleType role, Authentication authentication) {
		return getUser(authentication.getName()).getRole().equals(role);
	}
}