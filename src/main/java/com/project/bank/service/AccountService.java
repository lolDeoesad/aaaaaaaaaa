package com.project.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.bank.domain.Account;
import com.project.bank.domain.User;
import com.project.bank.repository.AccountRepository;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;
	
	public Account getAccount(int id) {
		return accountRepository.findById(id).get();
	}

	public Account insertAccount(User user) {
		if(!user.canOpenAccount())
			return null;
		Account account = new Account();
		account.setUser(user);
		account.setBalance(0);
		return accountRepository.save(account);
	}

	public void deleteAccount(int id) {
//		accountRepository.deleteById(id);
//		new Timestamp(System.currentTimeMillis());
	}
}