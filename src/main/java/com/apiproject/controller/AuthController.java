package com.apiproject.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apiproject.entities.Role;
import com.apiproject.entities.User;
import com.apiproject.payload.JWTAuthResponse;
import com.apiproject.payload.LoginDto;
import com.apiproject.payload.SignUpDto;
import com.apiproject.repositories.RoleRepository;
import com.apiproject.repositories.UserRepository;
import com.apiproject.security.JwtTokenProvider;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	   @Autowired  
	   private AuthenticationManager authenticationManager; 
	   @Autowired
	   private UserRepository userRepo;
	   @Autowired
	   private RoleRepository roleRepo;
	   @Autowired
	   private PasswordEncoder passwordEncoder;
	   
	    @Autowired
	    private JwtTokenProvider tokenProvider;

	   @PostMapping("/signin")  
	   public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto){ 
		   Authentication authentication = authenticationManager.authenticate(new
	  UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(),
	  loginDto.getPassword())); 
	  SecurityContextHolder.getContext().setAuthentication(authentication);
	  
      // get token form tokenProvider
      String token = tokenProvider.generateToken(authentication);

      return ResponseEntity.ok(new JWTAuthResponse(token));

	   }
	   
	   @PostMapping("/signUp")
	   public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){
		   
		   if(userRepo.existsByUsername(signUpDto.getUsername())) {
			 return new ResponseEntity<>("Username is already taken!!", HttpStatus.BAD_REQUEST) ; 
		   }
		   
		   if(userRepo.existsByEmail(signUpDto.getEmail())) {
			 return new ResponseEntity<> ("Email is already taken!!", HttpStatus.BAD_REQUEST) ; 
		   }
		   
		   User user= new User();
		   user.setName(signUpDto.getName());
		   user.setEmail(signUpDto.getEmail());
		   user.setUsername(signUpDto.getUsername());
		   user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
		   
		   Role role = roleRepo.findByName("ROLE_ADMIN").get();
		   user.setRoles(Collections.singleton(role));
		   
		   userRepo.save(user);
		   return new ResponseEntity<>("User Registered Successfully..",HttpStatus.OK);
	   }
	 
}
