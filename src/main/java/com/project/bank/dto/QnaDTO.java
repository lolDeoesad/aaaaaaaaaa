package com.project.bank.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class QnaDTO {
	
	private int id;
	
	@NotBlank(message = "제목 필수 입력")
	private String title;
	
	@NotBlank(message = "내용 필수 입력")
	private String content;	
}
