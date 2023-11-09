package com.project.bank.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.bank.domain.RoleType;
import com.project.bank.domain.Transaction;
import com.project.bank.domain.User;
import com.project.bank.repository.TransactionRepository;

@Service
public class TransactionService {
	
	@Autowired
	private TransactionRepository transactionRepository;

	public Transaction get(User user, int transactionId) {
		if(!transactionRepository.existsById(transactionId))
			return null;
		
		Transaction transaction = transactionRepository.findById(transactionId).get();
		if(user.getRole().equals(RoleType.CUSTOMER) && !user.hasAccount(transaction.getAccount().getId()))
			return null;
				
		return transaction;
	}
	
	@Transactional
	public int insert(User user, int accountId, Transaction transaction) {
		if(!user.hasAccount(accountId))
			return 400;
		
		return 200;
	}

	@Transactional
	public int update(User user, int accountId, Transaction transaction) {
		if(!user.hasAccount(accountId))
			return 400;
		
		return 200;
	}
}