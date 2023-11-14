package com.project.bank.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.bank.domain.Qna;
import com.project.bank.dto.QnaDTO;
import com.project.bank.service.QnaService;

@RestController
public class QnaController {

	@Autowired
	private QnaService qnaService;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping("/qna")
	public ResponseEntity<?> getList(@PageableDefault(size=10, sort="id", direction=Direction.DESC) Pageable pageable){	
		return new ResponseEntity<>(qnaService.getList(pageable), HttpStatus.OK);
	}

	@GetMapping("/qna/{id}")
	public ResponseEntity<?> get(@PathVariable int id){	
		return new ResponseEntity<>(qnaService.get(id), HttpStatus.OK);
	}

	@PostMapping("/qna")
	public ResponseEntity<?> insert(@Valid @RequestBody QnaDTO qnaDTO, BindingResult bindingResult) {
		if(qnaService.insert(modelMapper.map(qnaDTO, Qna.class)) == null)
			return new ResponseEntity<>("Qna등록 실패", HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<>("Qna등록 완료", HttpStatus.OK);
	}

	@PutMapping("/qna/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody QnaDTO qnaDTO, BindingResult bindingResult, @PathVariable int id) {
		if(!qnaService.update(modelMapper.map(qnaDTO, Qna.class), id))
			return new ResponseEntity<>("Qna수정 실패", HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<>("Qna수정 완료", HttpStatus.OK);
	}

	@DeleteMapping("/qna/{id}")
	public ResponseEntity<?> delete(@PathVariable int id) {
		if(!qnaService.delete(id))
			return new ResponseEntity<>("Qna삭제 실패", HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<>("Qna삭제 완료", HttpStatus.OK);
	}
}