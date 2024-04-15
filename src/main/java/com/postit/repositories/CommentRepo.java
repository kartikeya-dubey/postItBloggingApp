package com.postit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.postit.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
