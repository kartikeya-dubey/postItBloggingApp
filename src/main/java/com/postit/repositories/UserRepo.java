package com.postit.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.postit.entities.User;

public interface UserRepo extends JpaRepository<User, Integer>{
	
	//Search by username
	@Query("select u from User u where u.name like:key")
	User searchByUsername(@Param("key") String username);
	
	//For loading user by username(email) as required by security.CustomUserDetailService
	Optional<User> findByEmail(String email);
	
}
