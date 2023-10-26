package com.project.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.bank.domain.Account;
import com.project.bank.repository.AccountRepository;

@Service
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	public void insertAccount() {
		Account account = new Account();
		account.setBalance(0);
		account.setUser(account.getUser());
		accountRepository.save(account);
	}
	
	
}