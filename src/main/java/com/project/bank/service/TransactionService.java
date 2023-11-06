package com.project.bank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.project.bank.domain.Transaction;
import com.project.bank.repository.TransactionRepository;

@Service
public class TransactionService {
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	public List<Transaction> getTransactionList(int id, Authentication authentication) {
		return null;
	}
	public Transaction getTransaction(int id, Authentication authentication) {
		return transactionRepository.findById(id).get();
	}
	
	public void insertTransaction(Transaction transaction, Authentication authentication) {
		
	}
}
