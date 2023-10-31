package com.project.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.bank.domain.User;
import com.project.bank.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private	UserService userService;
	
	@PostMapping("/user")
	public ResponseEntity<?> insertUser(@RequestBody User user) {
		userService.insertUser(user);
		return new ResponseEntity<>("회원가입 완료", HttpStatus.OK);
	}
	
	public User getUser(Authentication authentication) {
		String username = authentication.getName();
		return userService.getUser(username);
	}
	
	@PutMapping("/user")
	public ResponseEntity<?> updateUser(@RequestBody User user, Authentication authentication) {
		userService.updateUser(user, getUser(authentication));
		return new ResponseEntity<>("회원정보수정 완료", HttpStatus.OK);
	}
	
	@DeleteMapping("/user")
	public ResponseEntity<?> deleteUser(Authentication authentication) {
		userService.deleteUser(getUser(authentication));
		return new ResponseEntity<>("회원탈퇴 완료", HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user) {
		return userService.getResponseEntity(user.getUsername(), user.getPassword());
	}
	
	@GetMapping("/userInfo")
	public ResponseEntity<?> userInfo(Authentication authentication) {
		return new ResponseEntity<>(getUser(authentication), HttpStatus.OK);
	}
}
