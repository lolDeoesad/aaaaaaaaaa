package com.project.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.bank.domain.Transaction;
import com.project.bank.service.TransactionService;

@RestController
public class TransactionController {

	@Autowired
	private TransactionService transactionService;
	
	@GetMapping("/transfer")
	public ResponseEntity<?> getTransactionList(@RequestParam int id, Authentication authentication) {
		return new ResponseEntity<>(transactionService.getTransactionList(id, authentication), HttpStatus.OK);
	}

	@GetMapping("/transfer/{id}")
	public ResponseEntity<?> getTransaction(@PathVariable int id, Authentication authentication) {
		return new ResponseEntity<>(transactionService.getTransaction(id, authentication), HttpStatus.OK);
	}

	@PostMapping("/transfer")
	public ResponseEntity<?> insertTransaction(@RequestBody Transaction transaction, Authentication authentication) {
		transactionService.insertTransaction(transaction, authentication);
		return new ResponseEntity<>("계좌이체 완료", HttpStatus.OK);
	}
}