package com.postit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.postit.entities.Role;

public interface RoleRepo  extends JpaRepository<Role, Integer>{

}
