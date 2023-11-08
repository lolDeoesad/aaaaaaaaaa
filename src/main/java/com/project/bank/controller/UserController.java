package com.project.bank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.bank.domain.User;
import com.project.bank.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private	UserService userService;
	
	public User getAuthUser(Authentication authentication) {
		String username = authentication.getName();
		return userService.getUser(username);
	}
	
	@GetMapping("/user")
	public ResponseEntity<?> get(Authentication authentication) {
		return new ResponseEntity<>(getAuthUser(authentication), HttpStatus.OK);
	}

	@GetMapping("/hasUser")
	public ResponseEntity<?> hasUser(@RequestParam String username) {
		if(userService.hasUser(username))
			return new ResponseEntity<>("이미 존재하는 아이디입니다", HttpStatus.OK);
		return new ResponseEntity<>("사용 가능한 아이디입니다", HttpStatus.OK);
	}
	
	@PostMapping("/user")
	public ResponseEntity<?> insertUser(@RequestBody User user) {
		if(userService.insertUser(user) == null)
			return new ResponseEntity<>("회원가입 실패", HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<>("회원가입 완료", HttpStatus.OK);
	}
	
	@PutMapping("/user")
	public ResponseEntity<?> updateUser(@RequestBody User user, Authentication authentication) {
		if(!userService.updateUser(user, getAuthUser(authentication)))
			return new ResponseEntity<>("회원정보수정 실패", HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<>("회원정보수정 완료", HttpStatus.OK);
	}
	
	@DeleteMapping("/user")
	public ResponseEntity<?> deleteUser(Authentication authentication) {
		if(!userService.deleteUser(getAuthUser(authentication)))
			return new ResponseEntity<>("회원탈퇴 실패", HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<>("회원탈퇴 완료", HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user) {
		return userService.getResponseEntity(user.getUsername(), user.getPassword());
	}
	
	@GetMapping("/approval")
	public ResponseEntity<?> getWebUserList() {
		List<User> notUserList = userService.getWebUserList();
		return new ResponseEntity<>(notUserList, HttpStatus.OK);
	}
	
	@PostMapping("/approval")
	public ResponseEntity<?> updateWebUserRole(@RequestBody List<User> webUsers) {
		userService.updateWebUserRole(webUsers); // front 이름 바꿔야 할 걸 아마...?
		return new ResponseEntity<>("권한변경 완료", HttpStatus.OK);
	}	
}