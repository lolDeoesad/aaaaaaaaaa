package com.project.bank.dto;

import javax.validation.constraints.Positive;

import com.project.bank.domain.Account;

import lombok.Data;

@Data
public class TransactionDTO {
	@Positive(message = "송금할 계좌번호 확인 필요")
	private int accountId;
	
	@Positive(message = "송금할 금액은 양수만 가능")
	private int money;
	
	private String memo;
}