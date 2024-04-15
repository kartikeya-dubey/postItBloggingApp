package com.postit.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.postit.payloads.ApiResponse;
import com.postit.payloads.UserDto;
import com.postit.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	//POST-create user
	
	//For creating just one user at a time
	/*@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto)
	{
		UserDto createUserDto = this.userService.createUser(userDto);
		return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
	}*/
	
	@PostMapping("/")
	
	//Optimized: can add multiple users at a time
	public ResponseEntity<List<UserDto>> createUser(@Valid @RequestBody List<UserDto> newUsers)
	{
		for (UserDto userDto : newUsers) 
		{
			this.userService.createUser(userDto);
		}
		//return ResponseEntity.ok(newUsers);
		return new ResponseEntity<List<UserDto>>(newUsers,HttpStatus.CREATED);
	}
	
	//PUT-update user
	@PutMapping("/{userId}")
	
	//If API URI variable(userID) and crud method call variable(uid) are different then 
	//we add the URI placeholder next to @PathVariable to make right mapping of value
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,
			@PathVariable("userId") int uid)
	{
		UserDto updatedUserDto = this.userService.updateUser(userDto, uid);
		
		System.out.println("Updated user: "+updatedUserDto);
		
		//return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
		return ResponseEntity.ok(updatedUserDto);
	}
	
	//ADMIN role based service
	// DELETE -delete user
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	
	//ResponseEntity<?> : Add ? if we dont know the return type of CRUD method in service class
	
	//ResponseEntity<ApiResponse> : if return type in service class delete is void but
	//we want to return something (custom message) from helper class in our project
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable int userId)
	{
		this.userService.deleteUser(userId);
		
		//return ResponseEntity.ok(Map.of("message", "User Deleted Successfully!"));
		//if return type is ResponseEntity<?>
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully!", true),
				HttpStatus.OK); 
		//>> Create an API Response class in payload package with Value: String message and
		//boolean deletion success status.
	}
	
	
	//GET-fetch user
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers() {
		
		//List<UserDto> allUsers = this.userService.getAllUsers();
		//return ResponseEntity.ok(allUsers);
		
		return ResponseEntity.ok(this.userService.getAllUsers());
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable int userId) {
		
		//User user = this.userService.getUserById(userId);
		//return ResponseEntity.ok(user);
		
		return ResponseEntity.ok(this.userService.getUserById(userId));
	}
	
	//Searching
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<UserDto> searchPostByTitle( @PathVariable String keywords){
			
		UserDto searchResult=this.userService.searchUserByUsername(keywords);
		return new ResponseEntity<UserDto>(searchResult, HttpStatus.OK);
			
	}
}
