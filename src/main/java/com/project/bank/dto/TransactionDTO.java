package com.project.bank.dto;

import javax.validation.constraints.Positive;

import com.project.bank.domain.Account;

import lombok.Data;

@Data
public class TransactionDTO {
	
	private Account account;
	
	@Positive(message = "송금할 금액은 양수만 가능")
	private int money;
	
	private String memo;
}