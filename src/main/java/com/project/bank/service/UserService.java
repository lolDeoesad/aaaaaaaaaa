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

	public User getAuth(String username) {
		return userRepository.findByUsername(username).get();
	}
	
	public User get(String username) {
		User findUser = userRepository.findByUsername(username).orElse(null);
		if(findUser == null)
			return null;
		User sendUser = new User(findUser.getId(), findUser.getUsername(), 
									"", // frontend에서 "" 허용 관련 수정 후 ""로 바꿈
									findUser.getFname(), findUser.getRole(), findUser.getIdNo().substring(0, 8),
									findUser.getEmail(), findUser.getPhone(), findUser.getCountry(),
									findUser.getAddress(), findUser.getAddressDetail(),
									findUser.getJobName(), findUser.getTeamName(), findUser.getJobAddress(), 
									findUser.getJobAddressDetail(), findUser.getJobPhone(), findUser.getAgree(),
									findUser.getTime(), findUser.getLastUpdateTime(), findUser.getAccountList()
									);
		return sendUser;
	}
	
	public User insert(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole(RoleType.WEBUSER);
		return userRepository.save(user);	
	}
	
	@Transactional
	public boolean update(User user, User loginUser) {
		Integer id = loginUser.getId();
		if(id == null || !userRepository.existsById(id))
			return false;
		User oldUser = userRepository.findById(id).get();
		if(user.getPassword() != null && !user.getPassword().equals(""))
			oldUser.setPassword(passwordEncoder.encode(user.getPassword()));
		
		oldUser.setFname(user.getFname());
		oldUser.setEmail(user.getEmail());
		oldUser.setPhone(user.getPhone());
		oldUser.setCountry(user.getCountry());
		oldUser.setAddress(user.getAddress());
		oldUser.setAddressDetail(user.getAddressDetail());
		oldUser.setJobName(user.getJobName());
		oldUser.setTeamName(user.getTeamName());
		oldUser.setJobAddress(user.getJobAddress());
		oldUser.setJobAddressDetail(user.getJobAddressDetail());
		oldUser.setJobPhone(user.getJobPhone());
		return userRepository.save(oldUser) != null;
	}
	
	public boolean delete(User user) {
		Integer id = user.getId();
		if(id == null || !userRepository.existsById(id))
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