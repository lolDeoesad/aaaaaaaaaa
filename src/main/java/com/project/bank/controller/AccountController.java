package com.project.bank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.bank.domain.Account;
import com.project.bank.service.AccountService;
import com.project.bank.service.UserService;

@RestController
public class AccountController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private	UserService userService;

	@GetMapping("/account")
	public ResponseEntity<?> getAccountList() {
		List<Account> accountList = accountService.getAccountList();
		return new ResponseEntity<>(accountList, HttpStatus.OK);
	}
	
	@PostMapping("/account")
	public ResponseEntity<?> insertAccount(Authentication authentication) {
		accountService.insertAccount(userService.getUser(authentication.getName()));
		return new ResponseEntity<>("계좌개설 완료", HttpStatus.OK);
	}
	
	@DeleteMapping("/account")
	public ResponseEntity<?> deleteAccount(@RequestParam int id) {
		accountService.deleteAccount(id);
		return new ResponseEntity<>("계좌해지 완료", HttpStatus.OK);
	}
}