package com.project.bank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.bank.domain.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{
	public List<Account> findAllByOrderByIdDesc();
}