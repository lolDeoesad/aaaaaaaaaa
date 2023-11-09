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
	
	public Page<Qna> getList(Pageable pageable){
		return qnaRepository.findAll(pageable);
	}
	
	public Qna get(int id) {
		return qnaRepository.findById(id).get();
	}
	
	public Qna insert(Qna qna) {	
		return qnaRepository.save(qna);
	}
	
	@Transactional
	public boolean update(Qna qna, int id) {
		Qna oldQna = qnaRepository.findById(id).orElse(null);
		if(oldQna == null)
			return false;
		oldQna.setTitle(qna.getTitle());
		oldQna.setContent(qna.getContent());
//		qnaRepository.save(oldQna);
		return true;
	}
	
	public boolean delete(int id) {
		boolean result = qnaRepository.existsById(id);
		qnaRepository.deleteById(id);
		return result;
	}
	
	
}
