package com.project.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.bank.domain.User;
import com.project.bank.service.AccountService;
import com.project.bank.service.UserService;

@RestController
public class AccountController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private	UserService userService;

//	@GetMapping("/account")
//	public ResponseEntity<?> getAccountList() {
//		List<Account> accountList = accountService.getAccountList();
//		return new ResponseEntity<>(accountList, HttpStatus.OK);
//	}
	
	@GetMapping("/account/{id}")
	public ResponseEntity<?> getAccount(Authentication authentication, @PathVariable int id) {
		User loginUser = userService.getAuthUser(authentication.getName());
		return accountService.getAccount(loginUser, id);
	}
	
	@PostMapping("/account")
	public ResponseEntity<?> insertAccount(Authentication authentication) {
		User loginUser = userService.getAuthUser(authentication.getName());
		return accountService.insertAccount(loginUser);
	}
	
	@PostMapping("/account/{username}")
	public ResponseEntity<?> insertAccountWithManager(@PathVariable String username) {
		User user = userService.getAuthUser(username);
		return accountService.insertAccount(user);
	}

	@DeleteMapping("/account/{id}")
	public ResponseEntity<?> deleteAccount(Authentication authentication, @PathVariable int id) {
		User loginUser = userService.getAuthUser(authentication.getName());
		return accountService.deleteAccount(loginUser, id);
	}

	@DeleteMapping("/account/{id}/{username}")
	public ResponseEntity<?> deleteAccountWithManager(@PathVariable int id, @PathVariable String username) {
		User user = userService.getAuthUser(username);
		return accountService.deleteAccount(user, id);
	}
}