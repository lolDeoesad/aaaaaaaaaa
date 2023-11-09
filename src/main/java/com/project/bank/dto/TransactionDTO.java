package com.project.bank.dto;

import javax.validation.constraints.Positive;

import lombok.Data;

@Data
public class TransactionDTO {
	@Positive
	private int subjectAccountId;
	
	@Positive
	private int money;
}
