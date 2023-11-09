package com.project.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.bank.domain.Transaction;
import com.project.bank.service.TransactionService;

@RestController
public class TransactionController {

	@Autowired
	private TransactionService transactionService;
	
	@GetMapping("/transfer/{id}")
	public ResponseEntity<?> getTransaction(Authentication authentication, @PathVariable int id) {
		return new ResponseEntity<>(transactionService.getTransaction(id, authentication), HttpStatus.OK);
	}

	@PostMapping("/transfer")
	public ResponseEntity<?> insertTransaction(Authentication authentication, @RequestBody Transaction transaction) {
		transactionService.insertTransaction(transaction, authentication);
		return new ResponseEntity<>("계좌이체 완료", HttpStatus.OK);
	}
}