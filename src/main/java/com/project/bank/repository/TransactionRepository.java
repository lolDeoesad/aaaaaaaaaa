package com.project.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.bank.domain.Account;

@Repository
public interface TransactionRepository extends JpaRepository<Account, Integer>{
	
}
