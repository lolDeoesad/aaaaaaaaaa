package com.project.bank.service;

import java.sql.Timestamp;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.bank.domain.Account;
import com.project.bank.domain.RoleType;
import com.project.bank.domain.User;
import com.project.bank.repository.AccountRepository;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;
	
	public ResponseEntity<?> getAccount(User user, int id) {
		if(user.getRole().equals(RoleType.CUSTOMER) && !user.hasAccount(id))
			return new ResponseEntity<>("계좌조회 실패", HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<>(accountRepository.findById(id).get(), HttpStatus.OK);
	}

	public ResponseEntity<?> insertAccount(User user) {
		if(!user.canOpenAccount())
			return new ResponseEntity<>("계좌개설 제한", HttpStatus.BAD_REQUEST);
		
		Account account = new Account();
		account.setUser(user);
		account.setBalance(0);
		if(accountRepository.save(account) == null)
			return new ResponseEntity<>("계좌개설 실패", HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<>("계좌개설 완료", HttpStatus.OK);
	}

	@Transactional
	public ResponseEntity<?> deleteAccount(User user, int id) {
		if(user.canCloseAccount(id) <= 0)
			return new ResponseEntity<>("계좌해지 실패", HttpStatus.BAD_REQUEST);
		
		Account account = accountRepository.findById(id).get();
		account.setClosedTime(new Timestamp(System.currentTimeMillis()));
		
		return new ResponseEntity<>("계좌해지 완료", HttpStatus.OK);
	}
}