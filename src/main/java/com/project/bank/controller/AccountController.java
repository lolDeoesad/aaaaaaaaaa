package com.project.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.project.bank.service.AccountService;

@Controller
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@PostMapping("/account")
	public ResponseEntity<?> insertAccount() {
		
		accountService.insertAccount();
		
		return new ResponseEntity<>("제출 성공하셨습니다.", HttpStatus.OK);
	}
	
}
