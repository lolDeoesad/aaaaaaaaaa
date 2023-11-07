package com.project.bank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.bank.domain.User;
import com.project.bank.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private	UserService userService;
	
	@GetMapping("/user") // 요청 주소랑 함수명 모두 isUser로 바꾸면 좋겠음, service도... 
	public ResponseEntity<?> checkUser(@RequestParam String username) {
		System.out.println(username); // 클라이언트에서 요청받은 아이디랑 데이터 베이스 안에 아이디랑 비교할거임
		if(userService.checkUser(username) == true)
			return new ResponseEntity<>("이미 존재하는 아이디입니다", HttpStatus.OK);
		return new ResponseEntity<>("사용 가능한 아이디입니다", HttpStatus.OK);
	}
	
	@PostMapping("/user")
	public ResponseEntity<?> insertUser(@RequestBody User user) {
		userService.insertUser(user);
		return new ResponseEntity<>("회원가입 완료", HttpStatus.OK);
	}
	
	public User getUser(Authentication authentication) {
		String username = authentication.getName();
		return userService.getUser(username);
	}
	
	@PutMapping("/user")
	public ResponseEntity<?> updateUser(@RequestBody User user, Authentication authentication) {
		userService.updateUser(user, getUser(authentication));
		return new ResponseEntity<>("회원정보수정 완료", HttpStatus.OK);
	}
	
	@DeleteMapping("/user")
	public ResponseEntity<?> deleteUser(Authentication authentication) {
		userService.deleteUser(getUser(authentication));
		return new ResponseEntity<>("회원탈퇴 완료", HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user) {
		return userService.getResponseEntity(user.getUsername(), user.getPassword());
	}
	
	@GetMapping("/userInfo")
	public ResponseEntity<?> userInfo(Authentication authentication) {
		return new ResponseEntity<>(getUser(authentication), HttpStatus.OK);
	}
	
	@GetMapping("/approval")
	public ResponseEntity<?> getWebUserList() {
		List<User> notUserList = userService.notUserList();
		return new ResponseEntity<>(notUserList, HttpStatus.OK);
	}
	
	@PostMapping("/approval")
	public ResponseEntity<?> updateWebUserRole(@RequestBody List<User> webUsers) {
		userService.updateWebUserRole(webUsers); // front 이름 바꿔야 할 걸 아마...?
		return new ResponseEntity<>("권한변경 완료", HttpStatus.OK);
	}	
}