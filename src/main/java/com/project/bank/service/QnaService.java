package com.project.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.bank.domain.Qna;
import com.project.bank.repository.QnaRepository;

@Service
public class QnaService {
	
	@Autowired
	private QnaRepository qnaRepository;
	
	public Page<Qna> getQnaList(Pageable pageable){
		return qnaRepository.findAll(pageable);
	}
	
	public Qna getQna(int id) {
		return qnaRepository.findById(id).get();
	}
	
	public Qna insertQna(Qna qna) {	
		return qnaRepository.save(qna);
	}
	
	@Transactional
	public boolean updateQna(Qna qna, int id) {
		Qna oldQna = qnaRepository.findById(id).orElse(null);
		if(oldQna == null)
			return false;
		oldQna.setTitle(qna.getTitle());
		oldQna.setContent(qna.getContent());
//		qnaRepository.save(oldQna);
		return true;
	}
	
	public boolean deleteQna(int id) {
		boolean result = qnaRepository.existsById(id);
		qnaRepository.deleteById(id);
		return result;
	}
	
	
}
