package com.project.bank.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.bank.domain.Qna;
import com.project.bank.domain.RoleType;
import com.project.bank.dto.QnaDTO;
import com.project.bank.service.QnaService;
import com.project.bank.service.UserService;

@RestController
public class QnaController {
	
	@Autowired
	private QnaService qnaService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping("/qna")
	public ResponseEntity<?> testQna(@Valid @RequestBody QnaDTO qnaDTO, BindingResult bindingResult) {
		return new ResponseEntity<>(qnaService.getQna(qnaDTO.getId()), HttpStatus.OK);
	}
	public ResponseEntity<?> getQnaList(@PageableDefault(size=10, sort="id", direction=Direction.DESC) Pageable pageable){	
		return new ResponseEntity<>(qnaService.getQnaList(pageable), HttpStatus.OK);
	}
	
	@PostMapping("/qna")
	public ResponseEntity<?> insertQna(Authentication authentication, @Valid @RequestBody QnaDTO qnaDTO, BindingResult bindingResult) {
		if(!userService.hasRole(RoleType.ADMIN, authentication))
			return new ResponseEntity<>("관리자만 Qna등록 가능", HttpStatus.BAD_REQUEST);
		
		if(qnaService.insertQna(modelMapper.map(qnaDTO, Qna.class)) == null)
			return new ResponseEntity<>("Qna등록 실패", HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<>("Qna등록 완료", HttpStatus.OK);
	}

	@PutMapping("/qna")
	public ResponseEntity<?> updateQna(Authentication authentication, @Valid @RequestBody QnaDTO qnaDTO, BindingResult bindingResult) {
		if(!userService.hasRole(RoleType.ADMIN, authentication))
			return new ResponseEntity<>("관리자만 Qna수정 가능", HttpStatus.BAD_REQUEST);
		
		if(!qnaService.updateQna(modelMapper.map(qnaDTO, Qna.class)))
			return new ResponseEntity<>("Qna수정 실패", HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<>("Qna수정 완료", HttpStatus.OK);
	}
	
	@DeleteMapping("/qna/{id}")
	public ResponseEntity<?> deleteQna(Authentication authentication, @PathVariable int id) {
		if(!userService.hasRole(RoleType.ADMIN, authentication))
			return new ResponseEntity<>("관리자만 Qna삭제 가능", HttpStatus.BAD_REQUEST);
		
		if(!qnaService.deleteQna(id))
			return new ResponseEntity<>("Qna삭제 실패", HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<>("Qna삭제 완료", HttpStatus.OK);
	}
}