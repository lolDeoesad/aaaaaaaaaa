package com.project.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
}
