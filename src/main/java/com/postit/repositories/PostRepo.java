package com.postit.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.postit.entities.Category;
import com.postit.entities.Post;
import com.postit.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer>{

	Page<Post> findByUser(User user, PageRequest p);
	Page<Post> findByCategory(Category category, PageRequest p);
	
	
	//Search
	
	//Search by title
	@Query("select p from Post p where p.title like:key")
	List<Post> searchByTitle(@Param("key") String title);
	
}
