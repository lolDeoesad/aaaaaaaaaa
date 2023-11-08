package com.project.bank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.bank.domain.Account;
import com.project.bank.domain.User;
import com.project.bank.repository.AccountRepository;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;

	public void insertAccount(User user) {
		Account account = new Account();
		account.setBalance(0);
		account.setUser(user);
		accountRepository.save(account);
	}

	public List<Account> getAccountList() {
		return accountRepository.findAllByOrderByIdDesc();
	}

	public void deleteAccount(int id) {
		accountRepository.deleteById(id);
	}
}
