package com.project.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.bank.domain.Account;
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

		Account account = accountService.getAccount(loginUser, id);
		if(account == null)
			return new ResponseEntity<>("계좌조회 실패", HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<>(account, HttpStatus.OK);
	}
	
	@PostMapping("/account")
	public ResponseEntity<?> insertAccount(Authentication authentication) {
		User loginUser = userService.getAuthUser(authentication.getName());
		int result = accountService.insertAccount(loginUser);
		return new ResponseEntity<>(HttpStatus.valueOf(result));

//		지금은 이해를 돕기 위해 message를 넣지만 차후에는 status만 보내도록 변경해서 if문 분기 없앨 예정!
//		return new ResponseEntity<>(HttpStatus.valueOf(result)); (수정할 거면 일단 동작하는지 확인부터 하고...)
//		if(result == 400) 
//			return new ResponseEntity<>("계좌개설 제한", HttpStatus.BAD_REQUEST);
//
//		if(result == 500)
//			return new ResponseEntity<>("계좌개설 실패", HttpStatus.BAD_REQUEST);
//
//		return new ResponseEntity<>("계좌개설 완료", HttpStatus.OK);
	}
	
	@PostMapping("/account/{username}")
	public ResponseEntity<?> insertAccountWithManager(@PathVariable String username) {
		User user = userService.getAuthUser(username);
		int result = accountService.insertAccount(user);
		return new ResponseEntity<>(HttpStatus.valueOf(result));
		
//		if(result == 400)
//			return new ResponseEntity<>("계좌개설 제한", HttpStatus.BAD_REQUEST);
//
//		if(result == 500)
//			return new ResponseEntity<>("계좌개설 실패", HttpStatus.BAD_REQUEST);
//
//		return new ResponseEntity<>("계좌개설 완료", HttpStatus.OK);
	}

	@DeleteMapping("/account/{id}")
	public ResponseEntity<?> deleteAccount(Authentication authentication, @PathVariable int id) {
		User loginUser = userService.getAuthUser(authentication.getName());
		int result = accountService.deleteAccount(loginUser, id);
		return new ResponseEntity<>(HttpStatus.valueOf(result));

//		if(result == 400)
//			return new ResponseEntity<>("계좌해지 실패", HttpStatus.BAD_REQUEST);
//		
//		return new ResponseEntity<>("계좌해지 완료", HttpStatus.OK);
	}

	@DeleteMapping("/account/{id}/{username}")
	public ResponseEntity<?> deleteAccountWithManager(@PathVariable int id, @PathVariable String username) {
		User user = userService.getAuthUser(username);
		int result = accountService.deleteAccount(user, id);
		return new ResponseEntity<>(HttpStatus.valueOf(result));

//		if(result == 400)
//			return new ResponseEntity<>("계좌해지 실패", HttpStatus.BAD_REQUEST);
//		
//		return new ResponseEntity<>("계좌해지 완료", HttpStatus.OK);
	}
}