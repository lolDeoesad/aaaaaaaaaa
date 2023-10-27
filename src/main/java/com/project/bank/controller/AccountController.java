package com.project.bank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.bank.domain.Account;
import com.project.bank.security.UserDetailsImpl;
import com.project.bank.service.AccountService;

@Controller
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@PostMapping("/account")
	public ResponseEntity<?> insertAccount(@AuthenticationPrincipal UserDetailsImpl principal) {
				
		accountService.insertAccount(principal.getUser());
		
		return new ResponseEntity<>("제출 성공하셨습니다.", HttpStatus.OK);
	}
	
	@GetMapping("/account")
	public ResponseEntity<?> getAccountList() {
		System.out.println("test");
		List<Account> accountList = accountService.getAccountList();
		return new ResponseEntity<>(accountList, HttpStatus.OK);
	}
	
	@DeleteMapping("/account")
	public ResponseEntity<?> deleteAccount(@RequestParam int id) {
		
		accountService.deleteAccount(id);
		
		return new ResponseEntity<>("계좌해지 성공", HttpStatus.OK);
	}
	
	
}
