package com.micro.service.app.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.micro.service.app.exception.UserNotFoundException;
import com.micro.service.app.model.Rating;
import com.micro.service.app.model.User;
import com.micro.service.app.service.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService  userService;
	
	@GetMapping("")
	public String test() {
		return "UserService";
	}
	
	@PostMapping("/save")
	public ResponseEntity<User> saveUser(HttpServletRequest request,@RequestBody User user){
		String authTokenHeader = request.getHeader("Authorization");
		System.out.println("UserController.getDataById()"+authTokenHeader);
	User userResp=	userService.saveUser(user,authTokenHeader);

	return ResponseEntity.status(HttpStatus.CREATED).body(userResp);
	}
	
	@GetMapping("/getAllData")
	public ResponseEntity<List<User>> getAllData(HttpServletRequest request){
		String authTokenHeader = request.getHeader("Authorization");
		System.out.println("UserController.getDataById()"+authTokenHeader);
	List<User> userResp=	userService.getAllData(authTokenHeader);

	return ResponseEntity.status(HttpStatus.OK).body(userResp);
	}
	
	
	
	@GetMapping("/get/{userId}")
	//@CircuitBreaker(name = "userserviceBreaker",fallbackMethod = "ratingServicFallBack")
	public ResponseEntity<User> getDataById(HttpServletRequest request,@PathVariable String userId)throws UserNotFoundException{
		String authTokenHeader = request.getHeader("Authorization");
		System.out.println("UserController.getDataById()"+authTokenHeader);
		User userResp=	userService.getDataById(userId,authTokenHeader);

	return ResponseEntity.status(HttpStatus.OK).body(userResp);
	}
	//creating fall back method for circuitbreaker with same name as mention in annotation and same return type 
		public  ResponseEntity<User> ratingServicFallBack(String userId,Exception ex){
			User user=new User();
			user.setUserId("dummy id" );
			user.setEmail("dumming@gmail.com");
			user.setAbout("Pune");
			return ResponseEntity.status(HttpStatus.OK).body(user);
		}
	
	
}
