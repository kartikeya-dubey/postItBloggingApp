package com.postit.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.postit.config.AppConstants;
import com.postit.entities.Role;
import com.postit.entities.User;
import com.postit.exceptions.ResourceNotFoundException;
import com.postit.payloads.UserDto;
import com.postit.repositories.RoleRepo;
import com.postit.repositories.UserRepo;
import com.postit.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private RoleRepo roleRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDto createUser(UserDto userDto) {

		User user=this.dtoToUserRepo(userDto);
		User savedUser=this.userRepo.save(user);
		
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, int userId) {
		
		Optional<User> optionalUser = userRepo.findById(userId);
		User user = optionalUser.orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));
		
		user.setAbout(userDto.getAbout());
		//user.setEmail(userDto.getEmail());
		user.setEmail(userDto.getEmail()==null ? user.getEmail() : userDto.getEmail());
		user.setName(userDto.getName());
		user.setPassword(userDto.getPassword());
		
		User updatedUser = this.userRepo.save(user);
		
		//UserDto userDto1 = this.userToDto(updatedUser);
		
		return this.userToDto(updatedUser);
	}

	@Override
	public UserDto getUserById(int userId) {
		Optional<User> optionalUser= userRepo.findById(userId);
		User user = optionalUser.orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));
		
		UserDto userDto = userToDto(user);
		
		return userDto;
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> allUsers = this.userRepo.findAll();
		
		List<UserDto> allUserDtos= allUsers.stream().map(
				user -> this.userToDto(user)).collect(Collectors.toList());
		
		return allUserDtos;
	}

	@Override
	public void deleteUser(int userId) {
		Optional<User> optionalUser = this.userRepo.findById(userId);
		User user = optionalUser.orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));
		
		this.userRepo.delete(user);
	}
	
	//Search user by username
	@Override
	public UserDto searchUserByUsername(String keyword) {
		
		User userSearched=this.userRepo.searchByUsername("%"+keyword+"%");
		
		//postSearched.forEach((post) -> System.out.println(post.getPostId()));
		
		UserDto userSearchedDto = this.modelMapper.map(userSearched, UserDto.class);
		
		//postSearchedDto.forEach((post) -> System.out.println(post.getPostId()));
		
		return userSearchedDto;
	}
	
	public User dtoToUserRepo(UserDto userDto)
	{
		/*User user=new User();
		user.setAbout(userDto.getAbout());
		user.setEmail(userDto.getEmail());
		user.setId(userDto.getId());
		user.setName(userDto.getName());
		user.setPassword(userDto.getPassword());*/
		
		
		//ModelMapper dependency is used to convert one class object into another
		//class object in optimized manner.
		//model mapper bean is defined in SpringBoot Application main page.
		User user = this.modelMapper.map(userDto, User.class);
		
		return user;
	}
	
	public UserDto userToDto(User user)
	{
		/*UserDto userDto=new UserDto();
		userDto.setAbout(user.getAbout());
		userDto.setEmail(user.getEmail());
		userDto.setId(user.getId());
		userDto.setName(user.getName());
		userDto.setPassword(user.getPassword());*/
		
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		
		return userDto;
	}
	
	@Override
	public UserDto registerNewUser(UserDto userDto) {

		User user = this.modelMapper.map(userDto, User.class);

		//encrypt the password during password fetch
		user.setPassword(new BCryptPasswordEncoder(10).encode(user.getPassword()));

		// roles
		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();

		user.getRoles().add(role);

		User newUser = this.userRepo.save(user);

		return this.modelMapper.map(newUser, UserDto.class);
	}

}
