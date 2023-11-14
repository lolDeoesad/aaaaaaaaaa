package com.project.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.bank.domain.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer>{
	
}
