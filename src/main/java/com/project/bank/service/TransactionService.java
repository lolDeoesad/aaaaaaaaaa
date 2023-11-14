package com.project.bank.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.bank.domain.Account;
import com.project.bank.domain.RoleType;
import com.project.bank.domain.Transaction;
import com.project.bank.domain.User;
import com.project.bank.repository.AccountRepository;
import com.project.bank.repository.TransactionRepository;

@Service
public class TransactionService {
	
	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private AccountRepository accountRepository;
	
	public Transaction get(User user, int transactionId) {
		if(!transactionRepository.existsById(transactionId))
			return null;
		
		Transaction transaction = transactionRepository.findById(transactionId).get();
		if(user.getRole().equals(RoleType.CUSTOMER) && !user.hasAccount(transaction.getAccount().getId()))
			return null;
				
		return transaction;
	}
	
	@Transactional
	public int insert(User user, int accountFromId, int accountToId, Transaction transaction) {
		/*
		 * 1. 사용자가 보내오는 정보
		 *    -. 로그인한 본인 정보
		 *    -. 거래를 진행할 본인 계좌번호 (요청 주소에 포함)
		 *    -. 상대방 accountid, 거래금액, 사용자 메모 (transactionDTO에 담겨옴)
		 * 2. 실제 거래는 account balance를 조절하면 서 생김
		 * 3. Transaction 2개 생성, 각각에 포함되는 것
		 *    -. account (Account에 transactionList로 종속)
		 *    -. money (보내는 사람은 -, 받는 사람은 +)
		 *    -. subject (받는 사람 fname)
		 *    -. memo (사용자 메모)
		 */
		if(!user.hasAccount(accountFromId))
			return 400;
		Account accountTo = accountRepository.findById(accountFromId).get();
		if((accountTo.getBalance() < transaction.getMoney()))
			return 400;
		
		accountTo.setBalance(accountTo.getBalance() - transaction.getMoney());
		Account accountFrom = accountRepository.findById(accountToId).get();
		accountFrom.setBalance(accountFrom.getBalance() + transaction.getMoney());
		
		Transaction transactionFrom = new Transaction(accountFrom, -transaction.getMoney(), accountTo.getUser().getFname(), transaction.getMemo()); 
		Transaction transactionTo = new Transaction(accountTo, transaction.getMoney(), accountFrom.getUser().getFname(), null);
		transactionRepository.save(transactionFrom);
		transactionRepository.save(transactionTo);
		return 200;
	}

	@Transactional
	public int update(User user, int transferId, Transaction transaction) {
//		if(!user.hasAccount(accountId))
//			return 400;
		Transaction oldTransaction = transactionRepository.findById(transferId).get();
		oldTransaction.setMemo(transaction.getMemo());
		return 200;
	}
}