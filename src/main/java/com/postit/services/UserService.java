package com.postit.services;

import java.util.List;

import com.postit.payloads.UserDto;

public interface UserService {
	
	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user, int userId);
	UserDto getUserById(int userId);
	List<UserDto> getAllUsers();
	void deleteUser(int userId);
	
	//Search user
	UserDto searchUserByUsername(String keyword);
	
	UserDto registerNewUser(UserDto user);
}
