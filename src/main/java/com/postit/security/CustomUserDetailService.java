package com.postit.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.postit.entities.User;
import com.postit.exceptions.ResourceNotFoundException;
import com.postit.repositories.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//Load user from DB by username
		
		User user=this.userRepo.findByEmail(username).orElseThrow(
				()-> new ResourceNotFoundException("User", "email: "+username, 0));
		
		return user;
	}

}
