package com.celebrateit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.celebrateit.dto.LoginRequestDTO;
import com.celebrateit.dto.SignupRequestDTO;
import com.celebrateit.dto.VerifyEmailDTO;
import com.celebrateit.pojo.Users;
import com.celebrateit.service.AuthService;

@RestController
@CrossOrigin(origins = "*")
public class AuthController {
	@Autowired
	AuthService authService;
	
	@PostMapping("/auth/login")
	public ResponseEntity<String> login(@RequestBody LoginRequestDTO dto){
		String jwt = authService.authenticate(dto);
		return ResponseEntity.ok(jwt);
	}
	
	@PostMapping("/auth/signup")
	public ResponseEntity<String> signup(@RequestBody SignupRequestDTO dto){
		String response = authService.sendOtp(dto);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/auth/verify")
	public ResponseEntity<?> verify(@RequestBody VerifyEmailDTO dto){
		try {
			String response = authService.addUser(dto);
			return ResponseEntity.ok(response);
		}
		catch(RuntimeException e) {
			return ResponseEntity.status(401).body(e.getMessage());
		}
	}
	
}
