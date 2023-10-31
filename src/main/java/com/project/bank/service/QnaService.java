package com.project.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.bank.repository.QnaRepository;

@Service
public class QnaService {
	
	@Autowired
	private QnaRepository qnaRepository;
	
}
