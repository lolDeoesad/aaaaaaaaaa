package com.project.bank.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.bank.domain.Transaction;
import com.project.bank.domain.User;
import com.project.bank.dto.TransactionDTO;
import com.project.bank.service.TransactionService;
import com.project.bank.service.UserService;

@RestController
public class TransactionController {

	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private	UserService userService;

	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping("/transfer/{transactionId}")
	public ResponseEntity<?> get(Authentication authentication, @PathVariable int transactionId) {
		User loginUser = userService.getAuth(authentication.getName());
		
		Transaction transaction = transactionService.get(loginUser, transactionId);
		if(transaction == null)
			return new ResponseEntity<>("계좌내역조회 실패", HttpStatus.BAD_REQUEST);

		return new ResponseEntity<>(transaction, HttpStatus.OK);
	}

	@PostMapping("/transfer/{accountFromId}")
	public ResponseEntity<?> insert(Authentication authentication, @PathVariable int accountFromId, @Valid @RequestBody TransactionDTO transactionDTO, BindingResult bindingResult) {
		User loginUser = userService.getAuth(authentication.getName());
		
		Transaction transaction = modelMapper.map(transactionDTO, Transaction.class);
		int result = transactionService.insert(loginUser, accountFromId, transactionDTO.getAccountId(), transaction);
		return new ResponseEntity<>(HttpStatus.valueOf(result));
//		return new ResponseEntity<>("계좌이체 완료", HttpStatus.OK);
	}
	
	@PutMapping("/transfer/{transactionId}")
	public ResponseEntity<?> update(Authentication authentication, @PathVariable int transactionId, @Valid @RequestBody TransactionDTO transactionDTO, BindingResult bindingResult) {
		User loginUser = userService.getAuth(authentication.getName());
		
		Transaction transaction = modelMapper.map(transactionDTO, Transaction.class);
		int result = transactionService.update(loginUser, transactionId, transaction);
		return new ResponseEntity<>(HttpStatus.valueOf(result));
//		return new ResponseEntity<>("계좌메모수정 완료", HttpStatus.OK);
	}
}