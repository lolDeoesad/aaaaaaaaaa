package com.project.bank.service;

import java.sql.Timestamp;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.bank.domain.Account;
import com.project.bank.domain.RoleType;
import com.project.bank.domain.User;
import com.project.bank.repository.AccountRepository;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;
	
	public Account get(User user, int id) {
		if(user.getRole().equals(RoleType.CUSTOMER) && !user.hasAccount(id))
			return null;
		
		return accountRepository.findById(id).get();
	}

	public int insert(User user) {
		if(!user.canOpenAccount())
			return 400;
		
		Account account = new Account();
		account.setUser(user);
		account.setBalance(0);
		if(accountRepository.save(account) == null)
			return 500;
		
		return 200;
	}

	@Transactional
	public int delete(User user, int id) {
		if(user.canCloseAccount(id) <= 0)
			return 400;
		
		Account account = accountRepository.findById(id).get();
		account.setClosedTime(new Timestamp(System.currentTimeMillis()));
		
		return 200;
	}
}