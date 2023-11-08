package com.project.bank.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
		User findUser = userRepository.findByUsername(username).orElse(null);
		if(findUser == null)
			return null;
		findUser.setPassword("비밀번호 변경을 원하실 경우에만 새로 입력하세요!"); // frontend에서 "" 허용 관련 수정 후 ""로 바꿀 예정	
		return findUser;
	}
	
	public User insertUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole(RoleType.WEBUSER);
		return userRepository.save(user);	
	}
	
	@Transactional
	public Boolean updateUser(User user, User loginUser) {
		Integer id = loginUser.getId();
		if(id == null || userRepository.existsById(id))
			return false;
		
		user.setId(loginUser.getId());
		// 고정해야 할 컬럼에 대한 거 고려하면 code 추가 필요함
		// eg. username 고정 필요할 경우 아래 주석 한줄 추가해야 함! ( → 이미 한줄 주석 해제함 )
		user.setUsername(loginUser.getUsername());
		if(user.getPassword() == null || user.getPassword().equals(""))
			user.setPassword(loginUser.getPassword());
		else
			user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole(loginUser.getRole());
		return userRepository.save(user) != null;
	}
	
	public boolean deleteUser(User user) {
		Integer id = user.getId();
		if(id == null || userRepository.existsById(id))
			return false;
		userRepository.deleteById(id);
		return true;
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
	
	public List<User> getWebUserList() {
		return userRepository.findByRole(RoleType.WEBUSER);
	}
	
	public boolean updateWebUserRole(List<User> webUsers) {
		boolean result = true;
		for(User user : webUsers) {
			user.setRole(RoleType.CUSTOMER);
			userRepository.save(user);
			if(!user.getRole().equals(RoleType.CUSTOMER))
				result = false;
		}
		return result;
	}
}