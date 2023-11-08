package com.project.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.bank.domain.RoleType;
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
		if(loginUser.getRole().equals(RoleType.CUSTOMER) && !loginUser.hasAccount(id))
			return new ResponseEntity<>("계좌조회 실패", HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<>(accountService.getAccount(id), HttpStatus.OK);
	}
	
	@PostMapping("/account")
	public ResponseEntity<?> insertAccount(Authentication authentication) {
		User loginUser = userService.getAuthUser(authentication.getName());
		if(accountService.insertAccount(loginUser) == null)
			return new ResponseEntity<>("계좌개설 실패", HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<>("계좌개설 완료", HttpStatus.OK);
	}
	
	@PostMapping("/account/{username}")
	public ResponseEntity<?> insertAccountWithManager(@PathVariable String username) {
		User user = userService.getAuthUser(username);
		if(accountService.insertAccount(user) == null)
			return new ResponseEntity<>("계좌개설 실패", HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<>("계좌개설 완료", HttpStatus.OK);
	}

	@DeleteMapping("/account/{id}")
	public ResponseEntity<?> deleteAccount(Authentication authentication, @PathVariable int id) {
//		accountService.deleteAccount(id);
		return new ResponseEntity<>("계좌해지 완료", HttpStatus.OK);
	}
}