package com.project.bank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.bank.domain.Account;

public interface AccountRepository extends JpaRepository<Account, Integer>{
	
	public List<Account> findAllByOrderByIdDesc();
	
}
