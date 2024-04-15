package com.postit;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.postit.config.AppConstants;
import com.postit.entities.Role;
import com.postit.repositories.RoleRepo;

@SpringBootApplication
public class PostItAppApisApplication implements CommandLineRunner{

	//To test Password Encoding
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(PostItAppApisApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}
	
	//To test Password Encoding
	@Override
	public void run(String... args) throws Exception {
		System.out.println(new BCryptPasswordEncoder().encode("testc")+" Test Password encoder executed in main");
		
	//Populate Role table with role IDs if not already.
		try {

			//Create new Role: Admin
			Role role = new Role();
			role.setId(AppConstants.ADMIN_USER);
			role.setName("ROLE_ADMIN");

			//Create another role: Normal
			Role role1 = new Role();
			role1.setId(AppConstants.NORMAL_USER);
			role1.setName("ROLE_NORMAL");

			List<Role> roles = List.of(role, role1);

			List<Role> result = this.roleRepo.saveAll(roles);

			//result.forEach(r -> {System.out.println(r.getName());});

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
}
